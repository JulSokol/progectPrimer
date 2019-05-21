package com.chess.service;

import com.chess.domain.Archive;
import com.chess.domain.ChessGame;
import com.chess.domain.User;
import com.chess.game.FigureMoving;
import com.chess.game.FigureOnSquare;
import com.chess.game.Square;
import com.chess.repos.ChessRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChessGameService {

    public final static String COLOR_WHITE = "white";
    public final static String COLOR_BLACK = "black";

    final char FIGURE_NONE = '1';

    final char PAWN_WHITE = 'P';
    final char KING_WHITE = 'K';
    final char QUEEN_WHITE = 'Q';
    final char ROOK_WHITE = 'R';
    final char BISHOP_WHITE = 'B';
    final char KNIGHT_WHITE = 'N';

    final char PAWN_BLACK = 'p';
    final char KING_BLACK = 'k';
    final char QUEEN_BLACK = 'q';
    final char ROOK_BLACK = 'r';
    final char BISHOP_BLACK = 'b';
    final char KNIGHT_BLACK = 'n';


    @Autowired public ChessRepo chessRepo;
    @Autowired public UserService userService;
    @Autowired public ArchiveService archiveService;

    /*public ChessGame newGame() {
        ChessGame board = new ChessGame();
        board.figures = "rnbqkbnrpppppppp11111111111111111111111111111111PPPPPPPPRNBQKBNR";
        chessRepo.save(board);
        return board;
    }
*/
    public ChessGame move(User user, long gameId, int frCoord, int toCoord) {
        ChessGame board = chessRepo.findById(gameId).get();

        FigureMoving fm = new FigureMoving(board.getFigure(new Square(frCoord)), frCoord, toCoord);

        if (board.moveColor.equals(COLOR_WHITE)) {
            if (!board.white.getId().equals(user.getId())) return board;
            if (!colorFigures(board.figures, fm.from).equals(COLOR_WHITE)) return board;
        } else {
            if (!board.black.getId().equals(user.getId())) return board;
            if (!colorFigures(board.figures, fm.from).equals(COLOR_BLACK)) return board;
        }

        if (canMove(board, fm) && !isCheckAfterMove(board, fm)){
            board.figures = moveFigure(board.figures, fm);
            if (board.moveColor.equals(COLOR_WHITE)) {
                board.moveColor = COLOR_BLACK;
            } else {
                board.moveColor = COLOR_WHITE;
            }
            chessRepo.save(board);
            if (isCheck(board)) {
                if (getAllMoves(board).isEmpty()) {
                    System.out.println("MAT!!!");
                } else {
                    System.out.println("ШАХ!!!");
                }
            }
        }
        return board;
    }

    private String moveFigure(String figures, FigureMoving fm) {
        StringBuilder figuresBuilder = new StringBuilder(figures);
        char figure = figuresBuilder.charAt(fm.from.getPositionIndex());
        figuresBuilder.setCharAt(fm.from.getPositionIndex(), '1');
        figuresBuilder.setCharAt(fm.to.getPositionIndex(), figure);
        return figuresBuilder.toString();
    }

    private boolean canMove(ChessGame board, FigureMoving fm){
        if("kK".indexOf(board.getFigure(fm.to)) >= 0 ){
            return false;
        }

        return canMoveFigure(board, fm);
    }

    private boolean canMoveFigure(ChessGame board, FigureMoving fm) {
        if(fm.from.equals(fm.to)) {
            return false;
        }
        if (colorFigures(board.figures, fm.from).equals(colorFigures(board.figures, fm.to))){
            return false;
        }
        switch (fm.figure) {
            case PAWN_WHITE:                       //пешка
            case PAWN_BLACK:
                return canPawnMove(board, fm);

            case KING_WHITE:                             //король
            case KING_BLACK:
                return canKingMove(fm);

            case QUEEN_WHITE:                        //королева
            case QUEEN_BLACK:
                return canQueenMove(fm, board);

            case ROOK_WHITE:
            case ROOK_BLACK:         //ладья
                return canRookMove(board, fm);

            case BISHOP_WHITE:
            case BISHOP_BLACK:
                return canBishopMove(board, fm);        //офицер

            case KNIGHT_WHITE:
            case KNIGHT_BLACK:
                return canKnightMove(fm); //конь

            default:
                return false;
        }
    }

    private boolean canBishopMove(ChessGame board, FigureMoving fm) {
        return (fm.getAbsDeltaX() == fm.getAbsDeltaY())
        && canStraightMove(fm, board);
    }

    private boolean canRookMove(ChessGame board, FigureMoving fm) {
        return (fm.getDeltaX() == 0 && fm.getAbsDeltaY() >= 1 || fm.getAbsDeltaX() >= 1 && fm.getDeltaY()== 0) &&
                canStraightMove(fm, board);
    }

    private boolean canQueenMove(FigureMoving fm, ChessGame board) {
        return canStraightMove(fm, board);
    }

    private boolean canStraightMove(FigureMoving fm, ChessGame board) {
        Square at = fm.from;
        do {
            at = new Square(at.x + fm.getSignX(), at.y + fm.getSignY());
            if (at.equals(fm.to)) {
                return true;
            }
        } while (at.isOnBoard() && board.getFigure(at) == FIGURE_NONE);
        return false;
    }


    private boolean canKnightMove(FigureMoving fm) {
        return (fm.getAbsDeltaX() == 1 && fm.getAbsDeltaY() == 2) || (fm.getAbsDeltaX() == 2 && fm.getAbsDeltaY() == 1);
    }

    private boolean canKingMove(FigureMoving fm) {
        return fm.getAbsDeltaX() <= 1 && fm.getAbsDeltaY() <= 1;
    }

    private boolean canPawnMove(ChessGame board, FigureMoving fm) {
        int stepY;
        if (colorFigures(board.figures, fm.from).equals(COLOR_WHITE)) {
            stepY = 1;
        } else {
            stepY = -1;
        }
        return
                canPawnGo(stepY, board, fm) ||
                canPawnJump(stepY, board, fm) ||
                canPawnEat(stepY, board, fm);
    }

    public boolean canPawnGo(int stepY, ChessGame board, FigureMoving fm) {
        if (board.getFigure(fm.to) == FIGURE_NONE){
            if (fm.getDeltaX() == 0){
                if (fm.getDeltaY() == stepY){
                    return true;
                }
            }
        }
       return false;
    }

    public boolean canPawnJump(int stepY, ChessGame board, FigureMoving fm) {
        if (board.getFigure(fm.to) == FIGURE_NONE) {
            if (fm.getDeltaX() == 0) {
                if (fm.getDeltaY() == stepY * 2) {
                    if (fm.from.y == 1 || fm.from.y == 6) {
                        if (board.getFigure(fm.from.nextY(stepY)) == FIGURE_NONE) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean canPawnEat(int stepY, ChessGame board, FigureMoving fm) {
        if (board.getFigure(fm.to) != FIGURE_NONE){
            if (fm.getAbsDeltaX() == 1){
                if (fm.getDeltaY() == stepY){
                    return true;
                }
            }
        }
        return false;
    }

    private String colorFigures(String figures, Square square) {
        return getFigureColor(figures.charAt(square.getPositionIndex()));
    }

    private String getFigureColor(char figure) {
        if ("KQRBNP".indexOf(figure) >= 0) {
            return COLOR_WHITE;
        }
        if ("kqrbnp".indexOf(figure) >= 0){
            return COLOR_BLACK;
        }
        return "empty";
    }

    public ChessGame getGame(long gameId) {
        return chessRepo.findById(gameId).get();
    }

    public ChessGame createGame(User whitePlayer, User blackPlayer) {
        ChessGame board = new ChessGame();
        board.figures = "rnbqkbnrpppppppp11111111111111111111111111111111PPPPPPPPRNBQKBNR";
        board.white = whitePlayer;
        board.black = blackPlayer;
        board.moveColor = COLOR_WHITE;
        chessRepo.save(board);
        archiveService.save(new Archive(whitePlayer, blackPlayer, LocalDateTime.now(), board.id));
        whitePlayer.setCurrentGameId(board.id);
        userService.save(whitePlayer);
        blackPlayer.setCurrentGameId(board.id);
        userService.save(blackPlayer);

        return board;
    }

    public boolean canEatKing(ChessGame board){
        Square badKing = findBadKing(board);
        for (FigureOnSquare fs: getAllFigures(board)) {
            FigureMoving fm = new FigureMoving(fs.getFigure(),
                    fs.getSquare().getPositionIndex(),
                    badKing.getPositionIndex());
            if (canMoveFigure(board, fm)) {
                return true;
            }
        }
        return false;
    }

    private Square findBadKing(ChessGame board) {
        char badKing = board.moveColor.equals(COLOR_WHITE) ? KING_BLACK : KING_WHITE;
        for (Square sq : getAllSquares(board.figures)) {
            if (board.getFigure(sq) == badKing) {
                return sq;
            }
        }
        return null;
    }

    private List<FigureMoving> getAllMoves(ChessGame board) {
        List<FigureMoving> list = new ArrayList<>();
        for (FigureOnSquare fs : getAllFigures(board)) {
            for (Square sq: getAllSquares(board.figures)) {
                FigureMoving fm = new FigureMoving(fs.getFigure(), fs.getSquare().getPositionIndex(), sq.getPositionIndex());
                if(canMove(board, fm) && !isCheckAfterMove(board, fm)) {
                    list.add(fm);
                }
            }
        }
        return list;
    }

    private List<FigureOnSquare> getAllFigures(ChessGame board) {
        List<FigureOnSquare> list = new ArrayList<>();
        for (int i = 0; i < board.figures.length(); i++) {
            if(getFigureColor(board.figures.charAt(i)).equals(board.moveColor) ) {
                list.add(new FigureOnSquare(new Square(i), board.figures.charAt(i)));
            }
        }
        return list;
    }

    private List<Square> getAllSquares(String figures) {
        List<Square> list = new ArrayList<>();
        for (int i = 0; i < figures.length(); i++) {
            list.add(new Square(i));
        }
        return list;
    }

    public boolean isCheck(ChessGame board) {
        board.moveColor = board.moveColor.equals(COLOR_WHITE) ? COLOR_BLACK : COLOR_WHITE;
        boolean result = canEatKing(board);
        board.moveColor = board.moveColor.equals(COLOR_WHITE) ? COLOR_BLACK : COLOR_WHITE;
        return result;
    }

    public boolean isCheckAfterMove(ChessGame board, FigureMoving fm) {
        String beforeMove = board.figures;
        board.figures = moveFigure(board.figures, fm);
        board.moveColor = board.moveColor.equals(COLOR_WHITE) ? COLOR_BLACK : COLOR_WHITE;
        boolean result = canEatKing(board);
        board.moveColor = board.moveColor.equals(COLOR_WHITE) ? COLOR_BLACK : COLOR_WHITE;
        board.figures = beforeMove;
        return result;
    }
}

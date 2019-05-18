package com.chess.service;

import com.chess.domain.ChessGame;
import com.chess.domain.User;
import com.chess.game.FigureMoving;
import com.chess.game.Square;
import com.chess.repos.ChessRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChessGameService {

    final String COLOR_WHITE = "white";
    final String COLOR_BLACK = "black";

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

    public ChessGame newGame() {
        ChessGame board = new ChessGame();
        board.figures = "rnbqkbnrpppppppp11111111111111111111111111111111PPPPPPPPRNBQKBNR";
        chessRepo.save(board);
        return board;
    }

    public ChessGame move(long gameId, int frCoord, int toCoord) {
        ChessGame board = chessRepo.findById(gameId).get();

        FigureMoving fm = new FigureMoving(board.getFigure(new Square(frCoord)), frCoord, toCoord);

        if (canMove(board, fm)){
            StringBuilder figuresBuilder = new StringBuilder(board.figures);
            char figure = figuresBuilder.charAt(frCoord);
            figuresBuilder.setCharAt(frCoord, '1');
            figuresBuilder.setCharAt(toCoord, figure);
            board.figures = figuresBuilder.toString();
            chessRepo.save(board);
        }
        return board;
    }

    private boolean canMove(ChessGame board, FigureMoving fm){
        if(fm.from.equals(fm.to)) {
            return false;
        }
        if("kK".indexOf(board.getFigure(fm.to)) >= 0 ){
            return false;
        }
        if (colorFigures(board, fm.from).equals(colorFigures(board, fm.to))){
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

   /* private boolean canBishopMove(FigureMoving fm) {
        return "";
    }*/

    private boolean canKnightMove(FigureMoving fm) {
        return (fm.getAbsDeltaX() == 1 && fm.getAbsDeltaY() == 2) || (fm.getAbsDeltaX() == 2 && fm.getAbsDeltaY() == 1);
    }

    private boolean canKingMove(FigureMoving fm) {
        return fm.getAbsDeltaX() <= 1 && fm.getAbsDeltaY() <= 1;
    }

    private boolean canPawnMove(ChessGame board, FigureMoving fm) {
        int stepY;
        if (colorFigures(board, fm.from).equals(COLOR_WHITE)) {
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



    private String colorFigures(ChessGame board, Square square) {
        if ("KQRBNP".indexOf(board.figures.charAt(square.getPositionIndex())) >= 0) {
            return COLOR_WHITE;
        }
        if ("kqrbnp".indexOf(board.figures.charAt(square.getPositionIndex())) >=0){
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

        whitePlayer.setCurrentGameId(board.id);
        userService.save(whitePlayer);
        blackPlayer.setCurrentGameId(board.id);
        userService.save(blackPlayer);

        return board;
    }
}

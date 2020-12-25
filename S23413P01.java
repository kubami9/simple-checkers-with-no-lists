import java.util.Scanner;

public class S23413P01 {
    public static void main(String[] args) {
        drawTable();
        makeTurn();
    }

    public static long whiteFirst = 0b101_000_000__101_010_000__101_001_001__101_000_010__101_010_010__101_001_011l;
    public static long whiteSecond = 0b101_000_100__101_010_100__101_001_101__101_000_110__101_010_110__101_001_111l;
    public static long blackFirst = 0b100_111_001__100_111_011__100_111_101__100_111_111__100_110_000__100_110_010l;
    public static long blackSecond = 0b100_110_100__100_110_110__100_101_001__100_101_011__100_101_101__100_101_111l;
    public static boolean turn = true; // tura białego true, czarnego false
    public static Scanner scanner = new Scanner(System.in);

    public static void drawTable() {
        char whiteBg = 0x2B1B;
        char blackBg = 0x2B1C;
        char whitePawn = 0x2659;
        char blackPawn = 0x265F;
        char whiteQueen = 0x2655;
        char blackQueen = 0x265B;
        int diagonalModifier = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0 + diagonalModifier; j < 8 + diagonalModifier; j++) {
                long currentPawnSet = whiteFirst;
                byte pawnSetCount = 0;
                // są maksymalnie 3 zmiany setów, więc przy 3 wiadomo że wykorzystano wszystkie
                while (currentPawnSet > 0 || pawnSetCount != 3) {
                    if (isAlive(currentPawnSet) == 1) {
                        int x = j - diagonalModifier;
                        int y = i;
                        long pawnX = getPosX(currentPawnSet);
                        long pawnY = getPosY(currentPawnSet);
                        if (pawnX == x && pawnY == y) {
                            if (getColor(currentPawnSet) == 1) {
                                System.out.print(whitePawn + " ");
                            } else {
                                System.out.print(blackPawn + " ");
                            }
                            break;
                        }
                    }
                    currentPawnSet = currentPawnSet >> 9;
                    if (currentPawnSet == 0 && pawnSetCount != 3) {
                        switch(pawnSetCount) {
                            case 0:
                                currentPawnSet = whiteSecond;
                                break;
                            case 1:
                                currentPawnSet = blackFirst;
                                break;
                            case 2:
                                currentPawnSet = blackSecond;
                                break;
                        }
                        pawnSetCount++;
                    }
                }
                if (currentPawnSet == 0 && pawnSetCount == 3) {
                    if (j % 2 == 0) {
                        System.out.print(whiteBg);
                    } else if (j % 2 != 0) {
                        System.out.print(blackBg);
                    }
                }
            }
            diagonalModifier++;
            System.out.println();
        }
        System.out.println();
    }

    public static void makeTurn() {
        long firstSet;
        long secondSet;
        boolean isWhite = false;
        String turnText = turn ? "białego" : "czarnego";
        if (turn) {
            turnText = "białego";
            firstSet = whiteFirst;
            secondSet = whiteSecond;
            isWhite = true;
        } else {
            turnText = "czarnego";
            firstSet = blackFirst;
            secondSet = blackSecond;
        }
        // TODO sprawdzić czy mogę zrobić ruch i go zrobić; czy drawTabel potrzebuje jeszcze argumenty?
        System.out.println("Ruch " + turnText + " gracza:");
        int column;
        int row;
        char moveDirection;
        while (true) {
            System.out.println("Podaj kolumnę piona 1-8:");
            column = scanner.nextInt() - 1;
            if (column < 0 || column > 7) {
                System.out.println("Nieprawidłowa wartość kolumny, podaj ją jeszcze raz:");
            } else {
                break;
            }
        }
        while (true) {
            System.out.println("Podaj wiersz piona 1-8:");
            row = scanner.nextInt() - 1;
            if (row < 0 || row > 7) {
                System.out.println("Nieprawidłowa wartość wiersza, podaj ją jeszcze raz:");
            } else {
                break;
            }
        }
        turn = !turn;
        // FIXME trzeba jeszcze sprawdzać kolor i w zależności od tego robić maskę (a z czasem i figurę, a w zasadzie to sprawdzać czy taki pion w ogóle jest i może być wybrany)
        long selectedPawn = (Long.parseLong(Integer.toBinaryString(column) + Integer.toBinaryString(row), 2)) | 0b100_000_000;

        while (true) {
            System.out.println("Wybierz ruch w lewo (L) lub prawo (R)");
            moveDirection = scanner.next().toUpperCase().charAt(0);
            if (moveDirection != 'R' && moveDirection != 'L') {
                System.out.println("Nieprawidłowa wartość kierunku, podaj ją jeszcze raz (L lub R):");
            } else {
                break;
            }
        }

        /* TODO
            jakoś sprytnie wymyślić przesuwanie pionka
            tj. decydować o kolorze i kierunku, zmienić oryginalną zmienną (podmiana referencji), sprawdzić czy pionek może przejść w dane miejsce, tzn. czy poprawny ruch
            a z czasem i sprawdzanie konieczności bicia, czy jest damka i pewnie coś tam jeszcze
        */

        System.out.println(Long.toBinaryString(selectedPawn) + " " + moveDirection);
    }

    public static long isAlive(long pawnInfo) {
        // 0 zbity, 1 w grze
        return (pawnInfo & 0b100000000) >> 8;
    }

    public static long getColor(long pawnInfo) {
        // 0 czarny, 1 biały
        return (pawnInfo & 0b1000000) >> 6;
    }

    public static long getFigure(long pawnInfo) {
        // 0 pion, 1 damka
        return (pawnInfo & 0b10000000) >> 7;
    }

    public static long getPosX(long pawnInfo) {
        return pawnInfo & 0b111;
    }

    public static long getPosY(long pawnInfo) {
        return (pawnInfo & 0b111000) >> 3;
    }
}

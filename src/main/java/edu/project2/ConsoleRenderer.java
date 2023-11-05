package edu.project2;

public final class ConsoleRenderer implements Renderer {

    private static final String RED_BACKGROUND = "\u001B[41m   ";
    private static final String BLACK_BACKGROUND = "\u001B[40m   ";
    private static final String ANSI_RESET = "\u001B[0m";
    public static Maze maze = null;

    public ConsoleRenderer(Maze maze) {
        ConsoleRenderer.maze = maze;
    }

    @Override
    public String render(Maze maze) {
        StringBuilder renderedMaze = new StringBuilder();
        for (int row = 0; row < maze.getHeight(); row++) {
            for (int col = 0; col < maze.getWidth(); col++) {
                Cell cell = maze.getCell(row, col);
                if (cell.getType() == CellType.WALL) {
                    renderedMaze.append(RED_BACKGROUND);
                    if (col == maze.getWidth() - 1 && maze.getHeight() % 2 == 0) {
                        renderedMaze.append(RED_BACKGROUND);
                    }
                } else {
                    renderedMaze.append(BLACK_BACKGROUND);
                    if (col == maze.getWidth() - 1 && maze.getHeight() % 2 == 0) {
                        renderedMaze.append(RED_BACKGROUND);
                    }
                }
            }

            renderedMaze.append(ANSI_RESET + "\n");
            if (row == maze.getWidth() - 1 && maze.getHeight() % 2 == 0) {
                for (int i = 0; i <= maze.getWidth(); i++) {
                    renderedMaze.append(RED_BACKGROUND + ANSI_RESET);
                }
            }
        }

        return renderedMaze.toString();
    }
}

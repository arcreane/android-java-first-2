package com.example.lappdance;


public class Grid {

// Grid cell
    public static class GridCell {
        public int realValue;
        public int assumedValue;
        public boolean isInitial = false;
        public boolean [] marks = { false, false, false, false, false, false, false, false, false };

        public GridCell( int realValue ) {
            this.realValue = realValue;
        }

        public GridCell( int realValue, int isInitial ) {
            this.realValue = realValue;
            this.isInitial = isInitial == 1;
            if ( this.isInitial ) this.assumedValue = realValue;
        }
    }


    public GridLevel level;
    public boolean bigNumber = true;

    public int currentCellX = -1;
    public int currentCellY = -1;


    public GridCell[] [] cells;

    // level and cells
    private Grid(GridLevel level, GridCell[][] cells ) {
        this.level = level;
        this.cells = cells;
    }


    public int getSelectedValue() {
        // We need to know the current cell
        if ( this.currentCellX == -1 ) return 0;
        if ( this.currentCellY == -1 ) return 0;

        GridCell currentCell = this.cells[ this.currentCellY ] [ this.currentCellX ];
        return currentCell.assumedValue;
    }


    public void pushValue( int value ) {

        if ( this.currentCellX == -1 ) return;
        if ( this.currentCellY == -1 ) return;

        GridCell currentCell = this.cells[ this.currentCellY ] [ this.currentCellX ];

        if ( currentCell.isInitial ) return;

        if ( this.bigNumber ) {

            currentCell.assumedValue = value;
        } else {

            currentCell.marks[value-1] = ! currentCell.marks[value-1];
        }
    }


    public void clearCell() {

        if ( this.currentCellX == -1 ) return;
        if ( this.currentCellY == -1 ) return;

        GridCell currentCell = this.cells[ this.currentCellY ] [ this.currentCellX ];


        if ( currentCell.isInitial ) return;

        currentCell.assumedValue = 0;
        currentCell.marks = new boolean[] { false, false, false, false, false, false, false, false, false };
    }

    public static Grid getGrid( GridLevel level ) {

        if ( level != GridLevel.GOOD ) throw new RuntimeException( "Not Done" );

        // TODO add code for generate differents Grid for each level

        return new Grid( level, new GridCell[][] {
                { new GridCell(9,1), new GridCell(2,0), new GridCell(8,0),
                        new GridCell(7,1), new GridCell(5,0), new GridCell(4,0),
                        new GridCell(1,1), new GridCell(3,0), new GridCell(6,0) },
                { new GridCell(6,0), new GridCell(7,0), new GridCell(1,0),
                        new GridCell(8,1), new GridCell(2,0), new GridCell(3,1),
                        new GridCell(5,0), new GridCell(4,0), new GridCell(9,0) },
                { new GridCell(3,0), new GridCell(5,1), new GridCell(4,1),
                        new GridCell(9,0), new GridCell(1,1), new GridCell(6,0),
                        new GridCell(2,0), new GridCell(7,1), new GridCell(8,0) },

                { new GridCell(4,1), new GridCell(9,1), new GridCell(6,0),
                        new GridCell(2,0), new GridCell(3,0), new GridCell(7,0),
                        new GridCell(8,1), new GridCell(5,1), new GridCell(1,0) },
                { new GridCell(8,0), new GridCell(1,1), new GridCell(5,0),
                        new GridCell(4,1), new GridCell(6,0), new GridCell(9,1),
                        new GridCell(7,0), new GridCell(2,1), new GridCell(3,0) },
                { new GridCell(7,0), new GridCell(3,1), new GridCell(2,1),
                        new GridCell(5,0), new GridCell(8,0), new GridCell(1,0),
                        new GridCell(9,0), new GridCell(6,1), new GridCell(4,1) },

                { new GridCell(5,0), new GridCell(4,1), new GridCell(3,0),
                        new GridCell(1,0), new GridCell(9,1), new GridCell(2,0),
                        new GridCell(6,1), new GridCell(8,1), new GridCell(7,0) },
                { new GridCell(2,0), new GridCell(6,0), new GridCell(9,0),
                        new GridCell(3,1), new GridCell(7,0), new GridCell(8,1),
                        new GridCell(4,0), new GridCell(1,0), new GridCell(5,0) },
                { new GridCell(1,0), new GridCell(8,0), new GridCell(7,1),
                        new GridCell(6,0), new GridCell(4,0), new GridCell(5,1),
                        new GridCell(3,0), new GridCell(9,0), new GridCell(2,1) }
        });
    }

}
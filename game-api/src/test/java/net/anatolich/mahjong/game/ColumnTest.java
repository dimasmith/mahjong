package net.anatolich.mahjong.game;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class ColumnTest {

    @Test
    public void testConstructorAssignment() {
        final int x = 10;
        final int y = 20;

        final Column column = new Column(x, y);

        assertThat(column.getX(), is(x));
        assertThat(column.getY(), is(y));
    }

    @Test
    public void testEquals(){
        final int x = 10;
        final int y = 20;

        final Column column1 = new Column(x, y);
        final Column column2 = new Column(x, y);

        assertThat(column1, is(column2));

    }

    @Test
    public void testEquals_Failure(){
        final int x = 10;
        final int y = 20;
        final int other = 30;

        final Column column1 = new Column(x, y);
        final Column column2 = new Column(other, y);
        final Column column3 = new Column(x, other);

        assertThat(column1, is(not(column2)));
        assertThat(column1, is(not(column3)));
    }

    @Test
    public void testIsColumnOnTopOfColumn() {
        final Column baseColumn = new Column(2, 2);
        final Column atopColumn = new Column(2, 2);
        final Column columnNW = new Column(1, 1);
        final Column columnNE = new Column(3, 1);
        final Column columnSW = new Column(1, 3);
        final Column columnSE = new Column(3, 3);
        final Column asideColumn = new Column(0, 0);
        assertThat(Column.isColumnOnTopOfColumn(baseColumn, atopColumn), is(true));
        assertThat(Column.isColumnOnTopOfColumn(baseColumn, columnNW), is(true));
        assertThat(Column.isColumnOnTopOfColumn(baseColumn, columnNE), is(true));
        assertThat(Column.isColumnOnTopOfColumn(baseColumn, columnSW), is(true));
        assertThat(Column.isColumnOnTopOfColumn(baseColumn, columnSE), is(true));
        assertThat(Column.isColumnOnTopOfColumn(baseColumn, asideColumn), is(false));
    }

    @Test
    public void testIsOnTopOfColumn() {
        final Column baseColumn = new Column(2, 2);
        final Column atopColumn = new Column(2, 2);
        final Column columnNW = new Column(1, 1);
        final Column columnNE = new Column(3, 1);
        final Column columnSW = new Column(1, 3);
        final Column columnSE = new Column(3, 3);
        final Column asideColumn = new Column(0, 0);
        assertThat(atopColumn.isOnTopOf(baseColumn), is(true));
        assertThat(columnNW.isOnTopOf(baseColumn), is(true));
        assertThat(columnNE.isOnTopOf(baseColumn), is(true));
        assertThat(columnSW.isOnTopOf(baseColumn), is(true));
        assertThat(columnSE.isOnTopOf(baseColumn), is(true));
        assertThat(asideColumn.isOnTopOf(baseColumn), is(false));
    }
}

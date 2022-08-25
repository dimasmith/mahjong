package net.anatolich.mahjong.game;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * @author Dmytro Kovalchuk<dimasmith@gmail.com>
 */
public class ColumnTest {

    @Test
    public void testConstructorAssignment() {
        final int x = 10;
        final int y = 20;

        final Column column = new Column(x, y);

        Assertions.assertThat(column.getX()).isEqualTo(x);
        Assertions.assertThat(column.getY()).isEqualTo(y);
    }

    @Test
    public void testEquals() {
        final int x = 10;
        final int y = 20;

        final Column column1 = new Column(x, y);
        final Column column2 = new Column(x, y);

        Assertions.assertThat(column1).isEqualTo(column2);

    }

    @Test
    public void testEquals_Failure() {
        final int x = 10;
        final int y = 20;
        final int other = 30;

        final Column column1 = new Column(x, y);
        final Column column2 = new Column(other, y);
        final Column column3 = new Column(x, other);

        Assertions.assertThat(column1).isNotEqualTo(column2)
                .isNotEqualTo(column3);
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

        Assertions.assertThat(Column.isColumnOnTopOfColumn(baseColumn, atopColumn)).isTrue();
        Assertions.assertThat(Column.isColumnOnTopOfColumn(baseColumn, columnNW)).isTrue();
        Assertions.assertThat(Column.isColumnOnTopOfColumn(baseColumn, columnNE)).isTrue();
        Assertions.assertThat(Column.isColumnOnTopOfColumn(baseColumn, columnSW)).isTrue();
        Assertions.assertThat(Column.isColumnOnTopOfColumn(baseColumn, columnSE)).isTrue();
        Assertions.assertThat(Column.isColumnOnTopOfColumn(baseColumn, asideColumn)).isFalse();
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

        Assertions.assertThat(atopColumn.isOnTopOf(baseColumn)).isTrue();
        Assertions.assertThat(columnNW.isOnTopOf(baseColumn)).isTrue();
        Assertions.assertThat(columnNE.isOnTopOf(baseColumn)).isTrue();
        Assertions.assertThat(columnSW.isOnTopOf(baseColumn)).isTrue();
        Assertions.assertThat(columnSE.isOnTopOf(baseColumn)).isTrue();
        Assertions.assertThat(asideColumn.isOnTopOf(baseColumn)).isFalse();
    }
}

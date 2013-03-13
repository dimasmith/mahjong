package net.anatolich.mahjong.desktopclient.display;

import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import net.anatolich.mahjong.game.Layout;

/**
 * Dialog for selecting layout from the list of supported layouts.
 *
 * @author Dmytro Kovalchuk
 * @since 0.2
 * @version 1.0
 */
public class SelectLayoutDialog extends JDialog {

    private final List<Layout> supportedLayouts;
    private Layout selectedLayout;

    public SelectLayoutDialog( Window owner, ModalityType modalityType, List<Layout> supportedLayouts ) {
        super( owner, modalityType );
        this.supportedLayouts = supportedLayouts;
        init();
    }

    private void init() {
        setTitle( "Select Layout" );
        setResizable( false );
        setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
        setLayout( new FlowLayout( FlowLayout.CENTER ) );

        createComponents();

        pack();
    }

    public Layout getSelectedLayout() {
        return selectedLayout;
    }

    private void createComponents() {
        for ( Layout layout : supportedLayouts ) {
            SelectLayoutAction action = new SelectLayoutAction( layout );
            JButton selectButton = new JButton( action );
            add( selectButton );
        }
    }

    private class SelectLayoutAction extends AbstractAction {

        private final Layout layout;

        private SelectLayoutAction( Layout layout ) {
            this.layout = layout;
            putValue( NAME, layout.getName() );
            putValue( SHORT_DESCRIPTION, layout.getDescription() );
        }

        @Override
        public void actionPerformed( ActionEvent e ) {
            selectedLayout = layout;
            dispose();
        }
    }
}

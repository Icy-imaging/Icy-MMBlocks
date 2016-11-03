package plugins.stef.micromanager.block.lang;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.micromanager.dialogs.ChannelTableModel;
import org.micromanager.utils.ChannelSpec;
import org.micromanager.utils.TooltipTextMaker;

import plugins.adufour.vars.gui.VarEditor;
import plugins.adufour.vars.lang.Var;
import plugins.tprovoost.Microscopy.MicroManager.gui.ChannelTable;

public class VarChannelsEditor extends VarEditor<ChannelSpec[]>
{
    protected JPanel mainPanel;
    protected ChannelTable table;

    public VarChannelsEditor(Var<ChannelSpec[]> variable)
    {
        super(variable);

        // can be done by super constructor
        if (mainPanel == null)
            initialize();

        setEditorEnabled(true);
    }

    @Override
    protected JPanel createEditorComponent()
    {
        // initialize if needed
        if (mainPanel == null)
            initialize();

        return mainPanel;
    }

    private void initialize()
    {
        table = new ChannelTable();

        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        // better to give it a very small size
        scroll.setMinimumSize(new Dimension(240, 60));
        scroll.setPreferredSize(new Dimension(240, 100));

        JButton btnAddChannel = new JButton("Add Channel");
        btnAddChannel.setToolTipText("Create new channel for currently selected channel group");
        btnAddChannel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                final ChannelTableModel model = (ChannelTableModel) table.getModel();
                model.addNewChannel();
                model.fireTableDataChanged();
            }
        });
        JButton btnRemoveChannel = new JButton("Remove Channel");
        btnRemoveChannel.setToolTipText("Remove currently selected channel");
        btnRemoveChannel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int sel = table.getSelectedRow();

                if (sel > -1)
                {
                    final ChannelTableModel model = (ChannelTableModel) table.getModel();
                    model.removeChannel(sel);
                    model.fireTableDataChanged();
                    if (table.getRowCount() > sel)
                        table.setRowSelectionInterval(sel, sel);
                }
            }
        });

        final JButton upButton = new JButton("Up");
        upButton.setToolTipText(TooltipTextMaker
                .addHTMLBreaksForTooltip("Move currently selected channel up (Channels higher on list are acquired first)"));
        upButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int sel = table.getSelectedRow();

                if (sel > -1)
                {
                    final ChannelTableModel model = (ChannelTableModel) table.getModel();
                    int newSel = model.rowUp(sel);
                    model.fireTableDataChanged();
                    table.setRowSelectionInterval(newSel, newSel);
                }
            }
        });

        final JButton downButton = new JButton("Down");
        downButton
                .setToolTipText(TooltipTextMaker
                        .addHTMLBreaksForTooltip("Move currently selected channel down (Channels lower on list are acquired later)"));
        downButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int sel = table.getSelectedRow();

                if (sel > -1)
                {
                    final ChannelTableModel model = (ChannelTableModel) table.getModel();
                    int newSel = model.rowDown(sel);
                    model.fireTableDataChanged();
                    table.setRowSelectionInterval(newSel, newSel);
                }
            }
        });

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));
        panelButtons.add(btnAddChannel);
        panelButtons.add(btnRemoveChannel);
        panelButtons.add(upButton);
        panelButtons.add(downButton);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(panelButtons, BorderLayout.NORTH);
        mainPanel.validate();
    }

    @Override
    protected void activateListeners()
    {
        //
    }

    @Override
    protected void deactivateListeners()
    {
        //
    }

    @Override
    protected void updateInterfaceValue()
    {
        //
    }

    public void setGroup(String group)
    {
        table.setGroup(group);
    }

    @Override
    public Dimension getPreferredSize()
    {
        if (mainPanel != null)
            return mainPanel.getPreferredSize();

        return new Dimension(400, 140);
    }

    @Override
    public void setComponentToolTipText(String s)
    {
        if (mainPanel != null)
            mainPanel.setToolTipText(s);
    }

    @Override
    protected void setEditorEnabled(boolean enabled)
    {
        if (mainPanel != null)
            mainPanel.setEnabled(enabled);
    }
}

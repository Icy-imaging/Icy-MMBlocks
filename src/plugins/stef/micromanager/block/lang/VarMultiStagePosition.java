package plugins.stef.micromanager.block.lang;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

import org.micromanager.api.MultiStagePosition;

import plugins.adufour.vars.gui.VarEditor;
import plugins.adufour.vars.lang.Var;
import plugins.adufour.vars.lang.VarArray;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;

public class VarMultiStagePosition extends VarArray<MultiStagePosition>
{
    VarEditorMultiStagePositions pos;

    public VarMultiStagePosition(String name, MultiStagePosition[] defaultValue)
    {
        super(name, MultiStagePosition[].class, defaultValue);

        // can be initialized with super constructor
        if (pos == null)
            pos = new VarEditorMultiStagePositions(this);
    }

    @Override
    public VarEditor<MultiStagePosition[]> createVarEditor()
    {
        if (pos == null)
            pos = new VarEditorMultiStagePositions(this);

        return pos;
    }

    private class VarEditorMultiStagePositions extends VarEditor<MultiStagePosition[]>
    {
        JButton btnOpen;

        public VarEditorMultiStagePositions(Var<MultiStagePosition[]> variable)
        {
            super(variable);
        }

        @Override
        protected JComponent createEditorComponent()
        {
            btnOpen = new JButton("Open");
            btnOpen.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    MicroManager.getMMStudio().showXYPositionList();
                }
            });

            return btnOpen;
        }

        @Override
        protected void activateListeners()
        {
        }

        @Override
        protected void deactivateListeners()
        {
        }

        @Override
        protected void updateInterfaceValue()
        {
        }

        @Override
        public Dimension getPreferredSize()
        {
            if (btnOpen != null)
                return btnOpen.getPreferredSize();

            return new Dimension(100, 24);
        }

        @Override
        public void setComponentToolTipText(String s)
        {
            if (btnOpen != null)
                btnOpen.setToolTipText(s);
        }

        @Override
        protected void setEditorEnabled(boolean enabled)
        {
            if (btnOpen != null)
                btnOpen.setEnabled(enabled);
        }
    }
}

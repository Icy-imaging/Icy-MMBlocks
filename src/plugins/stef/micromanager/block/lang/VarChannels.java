package plugins.stef.micromanager.block.lang;

import icy.type.collection.CollectionUtil;

import org.micromanager.utils.ChannelSpec;

import plugins.adufour.vars.gui.VarEditor;
import plugins.adufour.vars.lang.Var;
import plugins.adufour.vars.lang.VarArray;
import plugins.adufour.vars.util.VarListener;

public class VarChannels extends VarArray<ChannelSpec>
{
    VarChannelsEditor editor;

    public VarChannels(String name, EzVarMMGroup groupVar)
    {
        super(name, ChannelSpec[].class, null);

        // can be initialized by super constructor
        if (editor == null)
            editor = new VarChannelsEditor(this);

        setGroup(groupVar.getValue());

        groupVar.getVariable().addListener(new VarListener<String>()
        {
            @Override
            public void valueChanged(Var<String> source, String oldValue, String newValue)
            {
                setGroup(newValue);
            }

            @Override
            public void referenceChanged(Var<String> source, Var<? extends String> oldReference,
                    Var<? extends String> newReference)
            {
                //
            }
        });
    }

    public VarChannels(EzVarMMGroup groupVar)
    {
        this("Channels", groupVar);
    }

    @Override
    public VarEditor<ChannelSpec[]> createVarEditor()
    {
        if (editor == null)
            editor = new VarChannelsEditor(this);

        return editor;
    }

    @Override
    public ChannelSpec[] getValue()
    {
        return editor.table.getChannels().toArray(new ChannelSpec[0]);
    }

    @Override
    public void setValue(ChannelSpec[] value)
    {
        editor.table.setChannels(CollectionUtil.asArrayList(value));
    }

    public void setGroup(String groupName)
    {
        editor.setGroup(groupName);
    }

    // public static List<ChannelSpec> generateChannels(String groupName)
    // {
    // final List<ChannelSpec> toReturn = new ArrayList<ChannelSpec>();
    // if (groupName == null)
    // return toReturn;
    //
    // final List<String> presets = MicroManager.getConfigs(groupName);
    //
    // if (presets != null)
    // {
    // try
    // {
    // final String cam = MicroManager.getCamera();
    //
    // for (String s : presets)
    // {
    // ChannelSpec cs = new ChannelSpec();
    //
    // cs.config = s;
    // cs.camera = cam;
    // cs.color = Color.WHITE;
    // cs.doZStack = true;
    // cs.skipFactorFrame = 0;
    // cs.zOffset = 0D;
    // cs.exposure = 10;
    // cs.useChannel = true;
    //
    // toReturn.add(cs);
    // }
    // }
    // catch (Throwable t)
    // {
    // ReportingUtils.logError(t);
    // }
    // }
    //
    // return toReturn;
    // }

}

package plugins.stef.micromanager.block.capture;

import icy.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import mmcorej.TaggedImage;

import org.json.JSONObject;
import org.micromanager.utils.ChannelSpec;
import org.micromanager.utils.MDUtils;

import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.VarArray;
import plugins.adufour.vars.lang.VarObject;
import plugins.adufour.vars.util.VarException;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.stef.micromanager.block.lang.EzVarMMGroup;
import plugins.stef.micromanager.block.lang.VarChannels;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;
import plugins.tprovoost.Microscopy.MicroManager.tools.StageMover;

/**
 * Snap multi channel image (Micro-Manager)
 * 
 * @author Stephane
 */
public class MicroscopeSnapChannels extends AbstractMicroscopeBlock
{
    VarObject trigger;
    EzVarMMGroup group;
    VarChannels channels;
    VarArray<TaggedImage> out;

    public MicroscopeSnapChannels()
    {
        super();

        trigger = new VarObject("Trigger", null);
        group = new EzVarMMGroup();
        channels = new VarChannels(group);
        out = new VarArray<TaggedImage>("Tagged image(s)", TaggedImage[].class, new TaggedImage[0]);
    }

    @Override
    public void run()
    {
        // default
        out.setValue(new TaggedImage[0]);

        final ChannelSpec[] specs = channels.getValue();
        // no channel to process
        if (specs.length <= 0)
            return;

        try
        {
            MicroManager.setChannelGroup(group.getValue());
        }
        catch (Exception e)
        {
            throw new VarException(group.getVariable(), "Group value is not valid.");
        }

        final List<TaggedImage> result = new ArrayList<TaggedImage>();

        for (int ch = 0; ch < specs.length; ch++)
        {
            final ChannelSpec cs = specs[ch];

            // channel not used --> skip
            if (!cs.useChannel)
                continue;

            try
            {
                final String configName = cs.config;

                // set config
                if (!StringUtil.isEmpty(configName))
                    MicroManager.setConfigForGroup(MicroManager.getChannelGroup(), configName, true);
                // set exposure
                MicroManager.setExposure(cs.exposure);
                // doing Z-Stack ? --> apply Z-offset
                if (cs.doZStack.booleanValue() && (cs.zOffset != 0d))
                    StageMover.moveZRelative(cs.zOffset, true);

                // do image acquisition

                final List<TaggedImage> images = MicroManager.snapTaggedImage();

                // restore Z position if needed
                if (cs.doZStack.booleanValue() && (cs.zOffset != 0d))
                    StageMover.moveZRelative(-cs.zOffset, true);

                // no image ? --> error
                if (images.isEmpty())
                    throw new Exception("Cannot snap image from Micro-Manager !");

                // set metadata
                for (TaggedImage image : images)
                {
                    final JSONObject tags = image.tags;

                    MDUtils.setNumChannels(tags, specs.length);
                    MDUtils.setChannelIndex(tags, ch);
                    MDUtils.setChannelColor(tags, cs.color.getRGB());
                }

                // add to result
                result.addAll(images);
            }
            catch (Throwable t)
            {
                throw new VarException(out, t.getMessage());
            }
        }

        // set result
        out.setValue(result.toArray(new TaggedImage[result.size()]));
    }

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("trigger", trigger);
        inputMap.add("group", group.getVariable());
        inputMap.add("channels", channels);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        outputMap.add("out", out);
    }

    @Override
    public String getName()
    {
        return "Snap Channel(s)";
    }
}

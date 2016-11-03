package plugins.stef.micromanager.block.capture;

import java.util.List;

import mmcorej.TaggedImage;
import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.VarArray;
import plugins.adufour.vars.lang.VarObject;
import plugins.adufour.vars.util.VarException;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;

/**
 * Snap single channel image (Micro-Manager)
 * 
 * @author Stephane Dallongeville
 */
public class MicroscopeSnapImage extends AbstractMicroscopeBlock
{
    VarArray<TaggedImage> out = new VarArray<TaggedImage>("Tagged image(s)", TaggedImage[].class, new TaggedImage[0]);
    VarObject trigger = new VarObject("Trigger", null);

    @Override
    public void run()
    {
        try
        {
            final List<TaggedImage> images = MicroManager.snapTaggedImage();
            if (images.isEmpty())
                throw new Exception("Cannot snap image from Micro-Manager !");

            out.setValue(images.toArray(new TaggedImage[images.size()]));
        }
        catch (Throwable t)
        {
            throw new VarException(out, t.getMessage());
        }
    }

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("trigger", trigger);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        outputMap.add("out", out);
    }

    @Override
    public String getName()
    {
        return "Snap Image";
    }
}

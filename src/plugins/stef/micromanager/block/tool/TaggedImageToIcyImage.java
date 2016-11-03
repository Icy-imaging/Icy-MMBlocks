/**
 * 
 */
package plugins.stef.micromanager.block.tool;

import icy.image.IcyBufferedImage;
import icy.type.collection.CollectionUtil;
import mmcorej.TaggedImage;
import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.Var;
import plugins.adufour.vars.lang.VarArray;
import plugins.adufour.vars.util.VarException;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.tprovoost.Microscopy.MicroManager.tools.MMUtils;

/**
 * Block to convert a TaggedImage to an IcyBufferedImage (Micro-Manager)
 * 
 * @author Stephane
 */
public class TaggedImageToIcyImage extends AbstractMicroscopeBlock
{
    VarArray<TaggedImage> in = new VarArray<TaggedImage>("Tagged Image", TaggedImage[].class, new TaggedImage[0]);
    Var<IcyBufferedImage> out = new Var<IcyBufferedImage>("Icy Image", IcyBufferedImage.class);

    @Override
    public void run()
    {
        try
        {
            out.setValue(MMUtils.convertToIcyImage(CollectionUtil.asList(in.getValue())));
        }
        catch (Throwable t)
        {
            throw new VarException(out, t.getMessage());
        }
    }

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("taggedImage", in);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        outputMap.add("icyImage", out);
    }

    @Override
    public String getName()
    {
        return "TaggedImage to IcyBufferedImage";
    }
}
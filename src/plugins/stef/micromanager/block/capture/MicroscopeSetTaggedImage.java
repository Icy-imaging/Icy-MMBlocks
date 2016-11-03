/**
 * 
 */
package plugins.stef.micromanager.block.capture;

import mmcorej.TaggedImage;
import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.VarArray;
import plugins.adufour.vars.lang.VarInteger;
import plugins.adufour.vars.lang.VarSequence;
import plugins.adufour.vars.util.VarException;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.tprovoost.Microscopy.MicroManager.tools.MMUtils;

/**
 * Set a TaggedImage into a Sequence (Micro-Manager)
 * 
 * @author Stephane Dallongeville
 */
public class MicroscopeSetTaggedImage extends AbstractMicroscopeBlock
{
    VarArray<TaggedImage> taggedImages = new VarArray<TaggedImage>("Tagged image(s)", TaggedImage[].class,
            new TaggedImage[0]);
    VarSequence seq = new VarSequence("Sequence", null);
    VarInteger posT = new VarInteger("T", -1);
    VarInteger posZ = new VarInteger("Z", -1);
    VarInteger posC = new VarInteger("C", -1);
    VarInteger sizeT = new VarInteger("Size T", -1);
    VarInteger sizeZ = new VarInteger("Size Z", -1);
    VarInteger sizeC = new VarInteger("Size C", -1);

    @Override
    public void run()
    {
        try
        {
            for (TaggedImage image : taggedImages.getValue())
            {
                // set image position metadata
                MMUtils.setImageMetadata(image,  posT.getValue().intValue(), posZ.getValue().intValue(), posC
                        .getValue().intValue(), sizeT.getValue().intValue(), sizeZ.getValue().intValue(), sizeC.getValue().intValue());
                // store image in sequence
                MMUtils.setImage(seq.getValue(), image);
            }
        }
        catch (Throwable t)
        {
            throw new VarException(taggedImages, t.getMessage());
        }
    }

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("taggedImage", taggedImages);
        inputMap.add("seq", seq);
        inputMap.add("t", posT);
        inputMap.add("z", posZ);
        inputMap.add("c", posC);
        inputMap.add("sizeT", sizeT);
        inputMap.add("sizeZ", sizeZ);
        inputMap.add("sizeC", sizeC);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        //

    }

    @Override
    public String getName()
    {
        return "Set Tagged Image(s)";
    }
}

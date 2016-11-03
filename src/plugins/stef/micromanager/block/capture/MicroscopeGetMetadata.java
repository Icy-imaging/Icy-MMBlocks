/**
 * 
 */
package plugins.stef.micromanager.block.capture;

import org.json.JSONObject;

import plugins.adufour.blocks.util.VarList;
import plugins.adufour.vars.lang.Var;
import plugins.adufour.vars.lang.VarInteger;
import plugins.stef.micromanager.block.AbstractMicroscopeBlock;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;

/**
 * Retrieve global acquisition metadata and last acquired image metadata (Micro-Manager)
 * 
 * @author Stephane
 */
public class MicroscopeGetMetadata extends AbstractMicroscopeBlock
{
    VarInteger channel = new VarInteger("Channel", 0);
    Var<JSONObject> metadata = new Var<JSONObject>("Image metadata", JSONObject.class);
    Var<JSONObject> acqMetadata = new Var<JSONObject>("Acquisition metadata", JSONObject.class);

    @Override
    public void run()
    {
        metadata.setValue(MicroManager.getMetadata(channel.getValue().intValue()));
        acqMetadata.setValue(MicroManager.getAcquisitionMetaData());
    }

    @Override
    public void declareInput(VarList inputMap)
    {
        inputMap.add("channel", channel);
    }

    @Override
    public void declareOutput(VarList outputMap)
    {
        outputMap.add("imageMetadata", metadata);
        outputMap.add("acqMetadata", acqMetadata);
    }

    @Override
    public String getName()
    {
        return "Get Micro-Manager Metadata";
    }
}

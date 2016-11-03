/**
 * 
 */
package plugins.stef.micromanager.block;

import icy.gui.dialog.MessageDialog;
import icy.plugin.abstract_.Plugin;
import icy.plugin.interface_.PluginBundled;
import icy.plugin.interface_.PluginLibrary;
import mmcorej.CMMCore;

import org.micromanager.utils.ReportingUtils;

import plugins.adufour.blocks.lang.Block;
import plugins.tprovoost.Microscopy.MicroManager.MicroManager;
import plugins.tprovoost.Microscopy.MicroManagerForIcy.MicromanagerPlugin;

/**
 * @author Stephane
 */
public abstract class AbstractMicroscopeBlock extends Plugin implements Block, PluginLibrary, PluginBundled
{
    public AbstractMicroscopeBlock()
    {
        super();

        MicromanagerPlugin.init();

        if (!MicroManager.isInitialized())
            throw new RuntimeException("Cannot initialize Micro-Manager !");
    }

    @Override
    public String getMainPluginClassName()
    {
        return MicroscopyBlocks.class.getName();
    }

    public static CMMCore getMMCore(boolean throwException)
    {
        try
        {
            return MicroManager.getCore();
        }
        catch (Throwable e)
        {
            ReportingUtils.logError(e);
            if (throwException)
                throw new RuntimeException("Cannot retrieve Micro-Manager core !", e);

            MessageDialog.showDialog("Cannot find Micro-Manager...",
                    "You need to start Micro-Manager plugin before using Micro-Manager blocks !",
                    MessageDialog.WARNING_MESSAGE);

            // throw new VarException(null,
            // "Cannot retrieve Micro-Manager core...\nBe sure you started Micro-Manager plugin
            // before using Micro-Manager blocks !");
        }

        return null;
    }
}

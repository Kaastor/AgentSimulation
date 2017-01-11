package Environment;

import dissim.simspace.core.BasicSimContext;
import dissim.simspace.core.SimCalendarInterface;
import dissim.simspace.core.SimContextInterface;
import dissim.simspace.core.SimModel;

import lombok.Data;
import lombok.SneakyThrows;
import net.xeoh.plugins.base.annotations.PluginImplementation;

@Data
@PluginImplementation
public class SimulationContext extends BasicSimContext implements SimContextInterface{

    private int endSimTime = 100;

    public SimulationContext(){
        super();
    }
    @SneakyThrows
    @Override
    public void initContext() {
        System.out.println("initContext");
        SimModel.getInstance().setEndSimTime(endSimTime);
    }

    @Override
    public void stopContext() {
        System.out.println("stopContext");
    }

    @Override
    public SimCalendarInterface getSimCalendar() {
        return super.getSimCalendar();
    }
}

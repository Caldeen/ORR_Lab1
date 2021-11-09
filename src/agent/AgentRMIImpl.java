package agent;

import java.util.concurrent.TimeUnit;

public class AgentRMIImpl implements AgentRMIInterface {
    @Override
    public synchronized int operation(int x, int y, int op) {
        int result;
        if (op == 0)
            result = x + y;
        else
            result = x * y;
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}

package upb.thesis;

import soot.Unit;
import soot.jimple.IdentityStmt;
import soot.toolkits.graph.DirectedGraph;

import java.util.ArrayList;
import java.util.List;

public class CFGUtil {

    public static Unit getHead(DirectedGraph<Unit> graph) {
        List<Unit> heads = graph.getHeads();
        List<Unit> res = new ArrayList<>();
        for (Unit head : heads) {
            /*
            // because $stack26 := @caughtexception was coming in BriefUnitGraph heads along with this := @this: org.testng.junit.JUnit3TestRecognizer
            if (((JIdentityStmt) heads.get(1)).getRightOpBox().getValue() instanceof JCaughtExceptionRef){
                continue;
            }
             */
            if (head instanceof IdentityStmt || graph.getSuccsOf(head).isEmpty()) {
                continue;
            }
            res.add(head);
        }
        if (res.size() > 1) {
            throw new RuntimeException("multiple heads!");
        }
        if (res.isEmpty()) {
            return heads.get(0);
        }
        return res.get(0);
    }
}

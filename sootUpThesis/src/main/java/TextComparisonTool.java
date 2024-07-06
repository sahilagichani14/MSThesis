import org.apache.commons.io.IOUtils;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class TextComparisonTool extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextPane textPane1;
    private JTextPane textPane2;

    public TextComparisonTool() {
        setTitle("SootUp Textual Difference Analysis");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textPane1 = new JTextPane();
        textPane2 = new JTextPane();

        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(new JScrollPane(textPane1));
        panel.add(new JScrollPane(textPane2));

        getContentPane().add(panel);
    }

    public void compareTexts(String text1, String text2) {
        DiffMatchPatch dmp = new DiffMatchPatch();
        LinkedList<Diff> diffs = dmp.diffMain(text1, text2);
        dmp.diffCleanupSemantic(diffs);

        displayDiffs(textPane1, diffs, true);
        displayDiffs(textPane2, diffs, false);
    }

    private void displayDiffs(JTextPane textPane, LinkedList<Diff> diffs, boolean originalText) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet normal = new SimpleAttributeSet();
        SimpleAttributeSet insert = new SimpleAttributeSet();
        SimpleAttributeSet delete = new SimpleAttributeSet();

        StyleConstants.setBackground(insert, Color.GREEN);
        StyleConstants.setBackground(delete, Color.RED);

        try {
            doc.remove(0, doc.getLength());
            for (Diff diff : diffs) {
                AttributeSet attr = normal;
                if (diff.operation == DiffMatchPatch.Operation.INSERT && !originalText) {
                    attr = insert;
                } else if (diff.operation == DiffMatchPatch.Operation.DELETE && originalText) {
                    attr = delete;
                }
                doc.insertString(doc.getLength(), diff.text, attr);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TextComparisonTool tool = new TextComparisonTool();
            tool.setVisible(true);

            FileInputStream fis1 = null;
            FileInputStream fis2 = null;
            try {
                fis1 = new FileInputStream("sootupRes/jb_ls/in");
                String jimple1 = IOUtils.toString(fis1, "UTF-8");
                fis2 = new FileInputStream("sootupRes/jb_ls/out");
                String jimple2 = IOUtils.toString(fis2, "UTF-8");
                tool.compareTexts(jimple1, jimple2);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

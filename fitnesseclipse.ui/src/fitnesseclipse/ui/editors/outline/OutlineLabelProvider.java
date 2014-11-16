package fitnesseclipse.ui.editors.outline;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import fitnesseclipse.ui.FiteditUi;
import fitnesseclipse.ui.editors.outline.SegmentTree.NodeType;

public class OutlineLabelProvider extends LabelProvider {

    private static final String ICONS_DIR = "icons";

    private static Map<NodeType, ImageDescriptor> imageMap = null;

    @Override
    public Image getImage(Object element) {
        if (!(element instanceof SegmentTree)) {
            return super.getImage(element);
        }

        if (imageMap == null) {
            imageMap = new HashMap<SegmentTree.NodeType, ImageDescriptor>();
            imageMap.put(NodeType.FOLDING, FiteditUi.getImageDescriptor(ICONS_DIR + "/icon_C.gif"));
            imageMap.put(NodeType.TABLE, FiteditUi.getImageDescriptor(ICONS_DIR + "/icon_small_blue.gif"));
            imageMap.put(NodeType.HEADLINE, FiteditUi.getImageDescriptor(ICONS_DIR + "/icon_small_orange.gif"));
            imageMap.put(NodeType.DEFAULT, FiteditUi.getImageDescriptor(ICONS_DIR + "/icon_small_small_blue.gif"));
        }

        SegmentTree tree = (SegmentTree) element;
        ImageDescriptor descriptor = imageMap.get(tree.nodeType);
        if (descriptor != null) {
            return descriptor.createImage();
        }

        return super.getImage(element);
    }
}

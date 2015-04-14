package fitnesseclipse.ui.editors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.quickassist.IQuickAssistInvocationContext;
import org.eclipse.jface.text.quickassist.IQuickAssistProcessor;
import org.eclipse.jface.text.quickassist.QuickAssistAssistant;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.texteditor.MarkerAnnotation;

import fitnesseclipse.core.FitnesseEclipseCore;
import fitnesseclipse.ui.quickfix.CreateTestPageQuickfix;

public class FitnesseQuickAssistAssistant extends QuickAssistAssistant {
    class FitnesseQuickAssistantProcessor implements IQuickAssistProcessor {

        @Override
        public String getErrorMessage() {
            return null;
        }

        @Override
        public boolean canFix(Annotation annotation) {
            try {
                if (annotation instanceof MarkerAnnotation) {
                    MarkerAnnotation markerAnnotation = (MarkerAnnotation) annotation;
                    if (FitnesseEclipseCore.MARKER_TYPE.equals(markerAnnotation.getMarker().getType())) {
                        ((MarkerAnnotation) annotation).setQuickFixable(true);
                        ((MarkerAnnotation) annotation).update();
                        return true;
                    }
                }
            } catch (CoreException e) {
            }
            return false;
        }

        @Override
        public boolean canAssist(IQuickAssistInvocationContext invocationContext) {
            return false;
        }

        @Override
        public ICompletionProposal[] computeQuickAssistProposals(IQuickAssistInvocationContext invocationContext) {
            List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
            ISourceViewer viewer = invocationContext.getSourceViewer();
            Iterator<?> annotationIterator = viewer.getAnnotationModel().getAnnotationIterator();
            while (annotationIterator.hasNext()) {
                Annotation annotation = (Annotation) annotationIterator.next();
                if (annotation instanceof MarkerAnnotation) {
                    MarkerAnnotation markerAnnotation = (MarkerAnnotation) annotation;
                    Position position = viewer.getAnnotationModel().getPosition(markerAnnotation);
                    int start = position.getOffset();
                    int end = start + position.getLength();
                    if (invocationContext.getOffset() >= start && invocationContext.getOffset() <= end) {
                        proposals.add(new MarkerResolutionProposal(new CreateTestPageQuickfix(markerAnnotation
                                .getMarker()), markerAnnotation.getMarker()));
                    }
                }
            }
            return proposals.isEmpty() ? null : proposals.toArray(new ICompletionProposal[proposals.size()]);
        }
    }

    public FitnesseQuickAssistAssistant() {
        setQuickAssistProcessor(new FitnesseQuickAssistantProcessor());
    }
}

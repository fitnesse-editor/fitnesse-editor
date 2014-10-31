package fitedit.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import fitedit.editors.syntaxrules.ColorManager;
import fitedit.editors.syntaxrules.FitScanner;
import fitedit.editors.syntaxrules.FitSourcePartitionScanner;
import fitedit.editors.syntaxrules.IFitColorConstants;
import fitedit.editors.syntaxrules.NonRuleBasedDamagerRepairer;

public class FitSourceViewerConfiguration extends SourceViewerConfiguration {
    private static FitScanner fitScanner = null;
    private static ColorManager colorManager = new ColorManager();

    @Override
    public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
        List<IHyperlinkDetector> detectors = new ArrayList<IHyperlinkDetector>();
        for (IHyperlinkDetector detector : super.getHyperlinkDetectors(sourceViewer)) {
            detectors.add(detector);
        }
        detectors.add(new FitnesseLinkDetector());
        return detectors.toArray(new IHyperlinkDetector[detectors.size()]);
    }

    @Override
    public String[] getDefaultPrefixes(ISourceViewer sourceViewer, String contentType) {
        return new String[] { "#" };
    }

    @Override
    public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
        return new String[] { FitSourcePartitionScanner.FIT_COMMENT, FitSourcePartitionScanner.FIT_INCLUDE,
                FitSourcePartitionScanner.FIT_TABLE_HEADER, IDocument.DEFAULT_CONTENT_TYPE };
    }

    protected FitScanner getFitScanner() {
        if (fitScanner == null) {
            fitScanner = new FitScanner(colorManager);
            fitScanner.setDefaultReturnToken(new Token(new TextAttribute(colorManager
                    .getColor(IFitColorConstants.DEFAULT))));
        }
        return fitScanner;
    }

    @Override
    public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
        PresentationReconciler reconciler = new PresentationReconciler();

        DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getFitScanner());
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

        NonRuleBasedDamagerRepairer commentNdr = new NonRuleBasedDamagerRepairer(new TextAttribute(
                colorManager.getColor(IFitColorConstants.COMMENT)));
        reconciler.setDamager(commentNdr, FitSourcePartitionScanner.FIT_COMMENT);
        reconciler.setRepairer(commentNdr, FitSourcePartitionScanner.FIT_COMMENT);

        NonRuleBasedDamagerRepairer includeNdr = new NonRuleBasedDamagerRepairer(new TextAttribute(
                colorManager.getColor(IFitColorConstants.ACTION_BOLD)));
        reconciler.setDamager(includeNdr, FitSourcePartitionScanner.FIT_INCLUDE);
        reconciler.setRepairer(includeNdr, FitSourcePartitionScanner.FIT_INCLUDE);

        NonRuleBasedDamagerRepairer tableHeaderNdr = new NonRuleBasedDamagerRepairer(new TextAttribute(
                colorManager.getColor(IFitColorConstants.TABLE)));
        reconciler.setDamager(tableHeaderNdr, FitSourcePartitionScanner.FIT_TABLE_HEADER);
        reconciler.setRepairer(tableHeaderNdr, FitSourcePartitionScanner.FIT_TABLE_HEADER);

        return reconciler;
    }

    @Override
    public IContentFormatter getContentFormatter(ISourceViewer sourceViewer) {
        MultiPassContentFormatter formatter = new MultiPassContentFormatter(
                getConfiguredDocumentPartitioning(sourceViewer), IDocument.DEFAULT_CONTENT_TYPE);

        formatter.setMasterStrategy(new FitnesseFormattingStrategy());

        return formatter;
    }

}

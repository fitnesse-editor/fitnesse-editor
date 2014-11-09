package fitedit.ui.editors;

import java.util.Arrays;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlinkPresenter;
import org.eclipse.jface.text.hyperlink.MultipleHyperlinkPresenter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.quickassist.IQuickAssistAssistant;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.graphics.RGB;

import fitedit.ui.editors.syntaxrules.ColorManager;
import fitedit.ui.editors.syntaxrules.FitScanner;
import fitedit.ui.editors.syntaxrules.FitSourcePartitionScanner;
import fitedit.ui.editors.syntaxrules.IFitColorConstants;
import fitedit.ui.editors.syntaxrules.NonRuleBasedDamagerRepairer;

public class FitSourceViewerConfiguration extends SourceViewerConfiguration {
    private static FitScanner fitScanner = null;
    private static ColorManager colorManager = new ColorManager();

    private IQuickAssistAssistant assistant = new FitnesseQuickAssistAssistant();
    private IAnnotationHover hover = new DefaultAnnotationHover();

    @Override
    public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
        return hover;
    }

    @Override
    public IQuickAssistAssistant getQuickAssistAssistant(ISourceViewer sourceViewer) {
        return assistant;
    }

    @Override
    public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
        IHyperlinkDetector[] currentDetectors = super.getHyperlinkDetectors(sourceViewer);
        IHyperlinkDetector[] newDetectors = Arrays.copyOf(currentDetectors, currentDetectors.length + 1);
        newDetectors[currentDetectors.length] = new FitnesseLinkDetector();
        return newDetectors;
    }

    @Override
    public IHyperlinkPresenter getHyperlinkPresenter(ISourceViewer sourceViewer) {
        return new MultipleHyperlinkPresenter(new RGB(0, 0, 255));
    }

    @Override
    public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
        ContentAssistant assistant = new ContentAssistant();
        assistant.setContentAssistProcessor(new FitnesseContentAssistantProcessor(),
                FitSourcePartitionScanner.FIT_TABLE);
        assistant.enableAutoActivation(true);
        assistant.setAutoActivationDelay(500);
        assistant.setProposalPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);
        assistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);
        return assistant;
    }

    @Override
    public String[] getDefaultPrefixes(ISourceViewer sourceViewer, String contentType) {
        return new String[] { "#" };
    }

    @Override
    public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
        return new String[] { FitSourcePartitionScanner.FIT_COMMENT, FitSourcePartitionScanner.FIT_DEFINE,
                FitSourcePartitionScanner.FIT_INCLUDE, FitSourcePartitionScanner.FIT_TABLE,
                IDocument.DEFAULT_CONTENT_TYPE };
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

        DefaultDamagerRepairer damageRepairer = new DefaultDamagerRepairer(getFitScanner());
        reconciler.setDamager(damageRepairer, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(damageRepairer, IDocument.DEFAULT_CONTENT_TYPE);

        NonRuleBasedDamagerRepairer nonRuleBasedDamagerRepairer = new NonRuleBasedDamagerRepairer(new TextAttribute(
                colorManager.getColor(IFitColorConstants.COMMENT)));
        reconciler.setDamager(nonRuleBasedDamagerRepairer, FitSourcePartitionScanner.FIT_COMMENT);
        reconciler.setRepairer(nonRuleBasedDamagerRepairer, FitSourcePartitionScanner.FIT_COMMENT);

        nonRuleBasedDamagerRepairer = new NonRuleBasedDamagerRepairer(new TextAttribute(
                colorManager.getColor(IFitColorConstants.ACTION_BOLD)));
        reconciler.setDamager(nonRuleBasedDamagerRepairer, FitSourcePartitionScanner.FIT_INCLUDE);
        reconciler.setRepairer(nonRuleBasedDamagerRepairer, FitSourcePartitionScanner.FIT_INCLUDE);

        nonRuleBasedDamagerRepairer = new NonRuleBasedDamagerRepairer(new TextAttribute(
                colorManager.getColor(IFitColorConstants.TABLE)));
        reconciler.setDamager(nonRuleBasedDamagerRepairer, FitSourcePartitionScanner.FIT_TABLE);
        reconciler.setRepairer(nonRuleBasedDamagerRepairer, FitSourcePartitionScanner.FIT_TABLE);

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

package fitedit.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.TextEditorAction;

import fitedit.ui.editors.FitnesseEditor;

public class CommentHandler extends AbstractHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        FitnesseEditor editor = (FitnesseEditor) HandlerUtil.getActiveEditor(event);
        TextEditorAction action = (TextEditorAction) editor.getAction("comment");
        action.update();
        action.run();
        return null;
    }
}

// _MainMenu_EOArchive_English.java
// Generated by EnterpriseObjects palette at Sunday, April 4, 2004 1:38:31 PM US/Mountain

import com.webobjects.eoapplication.*;
import com.webobjects.eocontrol.*;
import com.webobjects.eointerface.*;
import com.webobjects.eointerface.swing.*;
import com.webobjects.foundation.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.text.*;

public class _MainMenu_EOArchive_English extends com.webobjects.eoapplication.EOArchive {
    ApplicationUtilities _applicationUtilities0;
    com.webobjects.eointerface.swing.EOFrame _eoFrame0;
    com.webobjects.eointerface.swing.EOImageView _nsImageView0;
    com.webobjects.eointerface.swing.EOTextField _nsTextField0, _nsTextField1;
    com.webobjects.eointerface.swing.EOView _nsProgressIndicator0;
    javax.swing.JPanel _nsView0;

    public _MainMenu_EOArchive_English(Object owner, NSDisposableRegistry registry) {
        super(owner, registry);
    }

    protected void _construct() {
        Object owner = _owner();
        EOArchive._ObjectInstantiationDelegate delegate = (owner instanceof EOArchive._ObjectInstantiationDelegate) ? (EOArchive._ObjectInstantiationDelegate)owner : null;
        Object replacement;

        super._construct();


        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "delegate")) != null)) {
            _applicationUtilities0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (ApplicationUtilities)replacement;
            _replacedObjects.setObjectForKey(replacement, "_applicationUtilities0");
        } else {
            _applicationUtilities0 = (ApplicationUtilities)_registered(new ApplicationUtilities(), "ApplicationUtilities");
        }

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "delegate.progress")) != null)) {
            _nsProgressIndicator0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOView)replacement;
            _replacedObjects.setObjectForKey(replacement, "_nsProgressIndicator0");
        } else {
            _nsProgressIndicator0 = (com.webobjects.eointerface.swing.EOView)_registered(new com.webobjects.eointerface.swing.EOView(), "11");
        }

        _nsTextField1 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField1");
        _nsTextField0 = (com.webobjects.eointerface.swing.EOTextField)_registered(new com.webobjects.eointerface.swing.EOTextField(), "NSTextField2");
        _nsImageView0 = (com.webobjects.eointerface.swing.EOImageView)_registered(new com.webobjects.eointerface.swing.EOImageView(), "");

        if ((delegate != null) && ((replacement = delegate.objectForOutletPath(this, "delegate.splashScreen")) != null)) {
            _eoFrame0 = (replacement == EOArchive._ObjectInstantiationDelegate.NullObject) ? null : (com.webobjects.eointerface.swing.EOFrame)replacement;
            _replacedObjects.setObjectForKey(replacement, "_eoFrame0");
        } else {
            _eoFrame0 = (com.webobjects.eointerface.swing.EOFrame)_registered(new com.webobjects.eointerface.swing.EOFrame(), "Splash Screen");
        }

        _nsView0 = (JPanel)_eoFrame0.getContentPane();
    }

    protected void _awaken() {
        super._awaken();
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "unhideAllApplications", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "hide", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "hideOtherApplications", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "orderFrontStandardAboutPanel", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(_owner(), "terminate", ), ""));

        if (_replacedObjects.objectForKey("_applicationUtilities0") == null) {
            _connect(_owner(), _applicationUtilities0, "delegate");
        }
    }

    protected void _init() {
        super._init();
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "openReportsSheet", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "checkSpelling", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "copy", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "importFile", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "performClose", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "runPageLayout", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "showHelp", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "showGuessPanel", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "redo", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "arrangeInFront", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "saveDocumentAs", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "undo", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "newDocument", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "performMiniaturize", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "selectAll", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "delete", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "toggleContinuousSpellChecking", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "clearRecentDocuments", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "exportFile", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "paste", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "openDocument", ), ""));
        .addActionListener((com.webobjects.eointerface.swing.EOControlActionAdapter)_registered(new com.webobjects.eointerface.swing.EOControlActionAdapter(null, "cut", ), ""));

        if (_replacedObjects.objectForKey("_applicationUtilities0") == null) {
            _connect(_applicationUtilities0, _eoFrame0, "splashScreen");
        }

        if (_replacedObjects.objectForKey("_applicationUtilities0") == null) {
            _connect(_applicationUtilities0, _nsProgressIndicator0, "progress");
        }

        _setFontForComponent(_nsTextField1, "Lucida Grande", 11, Font.PLAIN);
        _nsTextField1.setEditable(false);
        _nsTextField1.setOpaque(false);
        _nsTextField1.setText("Loading ...");
        _nsTextField1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        _nsTextField1.setSelectable(false);
        _nsTextField1.setEnabled(true);
        _setFontForComponent(_nsTextField0, "Lucida Grande", 13, Font.PLAIN);
        _nsTextField0.setEditable(false);
        _nsTextField0.setOpaque(false);
        _nsTextField0.setText("MacPAF");
        _nsTextField0.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        _nsTextField0.setSelectable(false);
        _nsTextField0.setEnabled(true);
        _nsTextField0.setBorder(null);
        if (!(_nsView0.getLayout() instanceof EOViewLayout)) { _nsView0.setLayout(new EOViewLayout()); }
        _nsImageView0.setSize(278, 161);
        _nsImageView0.setLocation(39, 11);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsImageView0, EOViewLayout.MinYMargin);
        _nsView0.add(_nsImageView0);
        _nsTextField0.setSize(61, 17);
        _nsTextField0.setLocation(146, 181);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsTextField0, EOViewLayout.MinYMargin);
        _nsView0.add(_nsTextField0);
        _nsTextField1.setSize(326, 22);
        _nsTextField1.setLocation(11, 200);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsTextField1, EOViewLayout.MinYMargin);
        _nsView0.add(_nsTextField1);
        _nsProgressIndicator0.setSize(16, 12);
        _nsProgressIndicator0.setLocation(317, 207);
        ((EOViewLayout)_nsView0.getLayout()).setAutosizingMask(_nsProgressIndicator0, EOViewLayout.MinYMargin);
        _nsView0.add(_nsProgressIndicator0);

        if (_replacedObjects.objectForKey("_eoFrame0") == null) {
            _nsView0.setSize(346, 228);
            _eoFrame0.setTitle("MacPAF");
            _eoFrame0.setLocation(403, 270);
            _eoFrame0.setSize(346, 228);
        }
    }
}

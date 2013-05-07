package UI;

import YFanalyze.closure;
import YYanalyze.Lab3;

import java.io.IOException;
import TranslateG.Translate;
import CFanalyze.CFanalyze;
import HBproduce.HBgenerate;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;


/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class NewSWTApp extends org.eclipse.swt.widgets.Composite {

	private Button HBproduce;
	private Button YFanalyze;
	private Button YYanalyze;
	private Button transform;
	private Button CFanalyze;
	private Text Code;
	private Label Title;
	String CFresult="";
	ReadFile readfile= new ReadFile();
	Translate translate=new Translate();
    closure clo=new closure();
	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	public NewSWTApp(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		try {
			this.setSize(602, 370);
			//setTitle("");
			this.setBackground(SWTResourceManager.getColor(253, 245, 230));
			FormLayout thisLayout = new FormLayout();
			this.setLayout(thisLayout);
			{
				HBproduce = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData HBanalyzeLData = new FormData();
				HBanalyzeLData.left =  new FormAttachment(0, 1000, 490);
				HBanalyzeLData.top =  new FormAttachment(0, 1000, 301);
				HBanalyzeLData.width = 98;
				HBanalyzeLData.height = 35;
				HBproduce.setLayoutData(HBanalyzeLData);
				HBproduce.setText("生成汇编");
			}
			{
				YYanalyze = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData YYanalyzeLData = new FormData();
				YYanalyzeLData.left =  new FormAttachment(0, 1000, 490);
				YYanalyzeLData.top =  new FormAttachment(0, 1000, 231);
				YYanalyzeLData.width = 98;
				YYanalyzeLData.height = 35;
				YYanalyze.setLayoutData(YYanalyzeLData);
				YYanalyze.setText("\u8bed\u4e49\u5206\u6790");
			}
			{
				YFanalyze = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData YFanalyzeLData = new FormData();
				YFanalyzeLData.left =  new FormAttachment(0, 1000, 490);
				YFanalyzeLData.top =  new FormAttachment(0, 1000, 161);
				YFanalyzeLData.width = 98;
				YFanalyzeLData.height = 35;
				YFanalyze.setLayoutData(YFanalyzeLData);
				YFanalyze.setText("\u8bed\u6cd5\u5206\u6790");
			}
			{
				transform = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData transformLData = new FormData();
				transformLData.left =  new FormAttachment(0, 1000, 490);
				transformLData.top =  new FormAttachment(0, 1000, 91);
				transformLData.width = 98;
				transformLData.height = 35;
				transform.setLayoutData(transformLData);
				transform.setText("\u7ffb\u8bd1TOKEN");
			}
			{
				CFanalyze = new Button(this, SWT.PUSH | SWT.CENTER);
				FormData CFanalyzeLData = new FormData();
				CFanalyzeLData.left =  new FormAttachment(0, 1000, 490);
				CFanalyzeLData.top =  new FormAttachment(0, 1000, 21);
				CFanalyzeLData.width = 98;
				CFanalyzeLData.height = 35;
				CFanalyze.setLayoutData(CFanalyzeLData);
				CFanalyze.setText("\u8bcd\u6cd5\u5206\u6790");
			}
			{
				Title = new Label(this, SWT.NONE);
				FormData label1LData = new FormData();
				label1LData.left =  new FormAttachment(0, 1000, 14);
				label1LData.top =  new FormAttachment(0, 1000,6);
				label1LData.width = 300;
				label1LData.height = 21;
				Title.setLayoutData(label1LData);
				Title.setText("小型编译器Made By 李琛轩");
				Title.setFont(new Font(Display.getCurrent(), "Cursive",13,SWT.NORMAL));
				Title.setForeground(new Color(Display.getCurrent(), 59, 49, 30));
				Title.setBackground(SWTResourceManager.getColor(253, 245, 230));
			}
			{
				Code = new Text(this, SWT.MULTI | SWT.WRAP);
				FormData CodeLData = new FormData();
				CodeLData.left =  new FormAttachment(0, 1000, 14);
				CodeLData.top =  new FormAttachment(0, 1000, 30);
				CodeLData.width = 454;
				CodeLData.height = 316;
				Code.setLayoutData(CodeLData);
				Code.setBackground(new Color(Display.getCurrent(), 240, 240, 240));
				Code.setForeground(new Color(Display.getCurrent(), 159, 149, 0));
				Code.setFont(new Font(Display.getCurrent(), "Cursive",12,SWT.NORMAL));
				Code.setText("Please Input your Code......");
			}
			CFanalyze.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					Code.setForeground(new Color(Display.getCurrent(), 159, 149, 222));
					String neelu=Code.getText();
					CFanalyze cfanalyze=new CFanalyze();
					cfanalyze.CFmain(neelu);
					try {
						Code.setText(readfile.readCFresult());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			transform.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					String neelu=Code.getText();
					Code.setText(translate.translateIt(neelu));
					
				}
			});
			final Lab3 lab3=new Lab3();
			YYanalyze.addSelectionListener(new SelectionAdapter() {
				@SuppressWarnings("static-access")
				public void widgetSelected(SelectionEvent e) {
					
					try {
						lab3.getUse();
						lab3.showAllUse();
						Code.setText(lab3.readOut());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			YFanalyze.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					String neelu=Code.getText();
					try {
						String getRES=clo.YFmain(neelu);
						if(getRES.charAt(0)=='H'){
							Code.setForeground(new Color(Display.getCurrent(), 113, 215, 113));
					    }
						else{
							Code.setForeground(new Color(Display.getCurrent(), 223, 0, 0));
						}
						Code.setText(clo.YFmain(neelu));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
			
			HBproduce.addSelectionListener(new SelectionAdapter() {
				@SuppressWarnings("static-access")
				public void widgetSelected(SelectionEvent e) {
					HBgenerate hb=new HBgenerate();
					hb.checkDoor(lab3.Quatlist);
					hb.makeHB(lab3.Quatlist);
					Code.setText(hb.HBres);
				}
			});
			this.layout();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Auto-generated main method to display this 
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		NewSWTApp inst = new NewSWTApp(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}


}

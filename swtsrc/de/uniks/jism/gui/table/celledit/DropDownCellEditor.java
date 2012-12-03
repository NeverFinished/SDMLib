package de.uniks.jism.gui.table.celledit;

import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.sdmlib.serialization.IdMap;
import org.sdmlib.serialization.interfaces.SendableEntityCreator;

public class DropDownCellEditor extends ComboBoxCellEditor implements CellEditorElement{
	private EditField editField;
	private SendableEntityCreator creator;
	public DropDownCellEditor (){
		
	}

	public DropDownCellEditor(Table table, IdMap map, SendableEntityCreator creator) {
		super(table, new String[]{});
		this.creator = creator;
		init(map);
	}
	public void init(IdMap map) {
		this.editField.createControl(EditField.FORMAT_COMBOX, map, creator);		
	}
	protected Control createControl(Composite parent) {
		Control comboBox = super.createControl(parent);
		editField=new EditField(this, parent);
		editField.setEditField(comboBox);
        return comboBox;
    }
	
	@Override
	protected Object doGetValue() {
		return editField.getText();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.TextCellEditor#doSetValue(java.lang.Object)
	 */
	@Override
	protected void doSetValue(Object value) {
		editField.doSetValue(value);
	}

	@Override
	public void defaultSelection(SelectionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyRelease(KeyEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFocusLost() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getEditorValue() {
		return editField.getValue();
	}
}
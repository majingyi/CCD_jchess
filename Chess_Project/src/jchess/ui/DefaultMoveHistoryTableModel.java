package jchess.ui;

import javax.swing.table.DefaultTableModel;

public class DefaultMoveHistoryTableModel extends DefaultTableModel {

	private static final long	serialVersionUID	= 7349152369577914941L;

	DefaultMoveHistoryTableModel() {
		super();
	}

	@Override
	public boolean isCellEditable(int a, int b) {
		return false;
	}
}

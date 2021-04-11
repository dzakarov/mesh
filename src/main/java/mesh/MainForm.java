package mesh;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

public class MainForm extends JFrame {
	private static final long serialVersionUID = 1L;

	JTable mGrid;
	PlayGroundModel mDataModel;

	private JScrollPane mGridView;
	private JButton mRandom;
	private JButton mClear;
	private JButton mProbCalc;

	private JButton getClear() {
		if (mClear == null) {
			mClear = new JButton();
			mClear.setText("Clear");
			mClear.addActionListener(new ClearAction(this));
		}
		return mClear;
	}

	private JButton getRandom() {
		if (mRandom == null) {
			mRandom = new JButton();
			mRandom.setText("Random");
			mRandom.addActionListener(new RandomAction(this));

		}
		return mRandom;

	}

	public Component getGridView() {
		if (mGridView == null) {
			mGridView = new JScrollPane(getGrid());
			mGridView.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

		}
		return mGridView;
	}

	public JTable getGrid() {
		if (mGrid == null) {
			mGrid = new JTable();

			setDataModel(new PlayGroundModel(this));
			mGrid.setModel(getDataModel());
			mGrid.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			mGrid.addKeyListener(new CellKeyListener(this));
			mGrid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			mGrid.setColumnSelectionAllowed(true);
			mGrid.setRowSelectionAllowed(true);
			mGrid.setTableHeader(null);

			final TableCellRenderer rndr = mGrid.getDefaultRenderer(Object.class);
			mGrid.setDefaultRenderer(Object.class, new OurCellRenderer(this, rndr));

		}
		return mGrid;

	}

	public MainForm() {
		Thread db = new Thread(new Runnable() {
			@Override
			public void run() {
			}
		});
		db.start();

		setSize(470, 520);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(getGridView(), GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(

						layout.createParallelGroup() //
								.addComponent(getRandom()) //
								.addComponent(getClear()) //
								.addComponent(getProbCalc()))//
//				.addGroup(
//
//						layout.createParallelGroup() //
//								//
//				)

		);

		layout.setHorizontalGroup( //
				layout.createParallelGroup() //
						.addComponent(getGridView(), 100, GroupLayout.DEFAULT_SIZE, Integer.MAX_VALUE) //
						.addGroup(

								layout.createSequentialGroup() //
										.addComponent(getProbCalc())//
										.addComponent(getRandom()) //
										.addComponent(getClear()) //

						//
						));

	}

	public static void main(String[] argv) {
		MainForm form = new MainForm();
		form.setVisible(true);

	}

	public void setDataModel(PlayGroundModel dataModel) {
		mDataModel = dataModel;
	}

	public PlayGroundModel getDataModel() {
		return mDataModel;
	}

	public void setGridView(JScrollPane gridView) {
		mGridView = gridView;
	}

	private JButton getProbCalc() {
		if (mProbCalc == null) {
			mProbCalc = new JButton("Calc");
			mProbCalc.addActionListener(new ProbabilityAction(this));
		}
		return mProbCalc;
	}

	void clear(int mod) {
		int size = getDataModel().getSize();

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (mod > 0) {
					String str = getDataModel().matrix[i][j];
					if ((str.indexOf("%") >= 0) || ("Flag".equals(str))) {
						getDataModel().matrix[i][j] = "";
					}

				} else {
					getDataModel().matrix[i][j] = "";
				}

			}
		}
		getDataModel().fireTableDataChanged();
	}

}

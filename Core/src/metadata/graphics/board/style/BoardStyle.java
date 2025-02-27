package metadata.graphics.board.style;

import java.util.BitSet;

import annotations.Hide;
import game.Game;
import game.equipment.container.board.custom.MancalaBoard;
import game.types.board.StoreType;
import metadata.graphics.GraphicsItem;
import metadata.graphics.util.ContainerStyleType;
import other.concept.Concept;

/**
 * Sets the style of the board.
 * 
 * @author Matthew.Stephenson
 */
@Hide
public class BoardStyle implements GraphicsItem
{
	/** Container style to apply. */
	private final ContainerStyleType containerStyleType;
	
	/** Don't draw any components, and fill their cells instead (only for pen and paper style) . */
	private final boolean replaceComponentsWithFilledCells;
		
	//-------------------------------------------------------------------------

	/**
	 * @param containerStyleType 					Container style wanted for the board.
	 * @param replaceComponentsWithFilledCells      True if cells should be filled instead of component drawn.
	 */
	public BoardStyle
	(
		final ContainerStyleType containerStyleType,
		final boolean replaceComponentsWithFilledCells
	)
	{
		this.containerStyleType = containerStyleType;
		this.replaceComponentsWithFilledCells = replaceComponentsWithFilledCells;
	}

	//-------------------------------------------------------------------------

	/**
	 * @return ComponentStyleType to apply onto component.
	 */
	public ContainerStyleType containerStyleType()
	{
		return containerStyleType;
	}
	
	/**
	 * @return True if cells should be filled instead of component drawn.
	 */
	public boolean replaceComponentsWithFilledCells()
	{
		return replaceComponentsWithFilledCells;
	}
	
	//-------------------------------------------------------------------------

	@Override
	public BitSet concepts(final Game game)
	{
		final BitSet concepts = new BitSet();
		if (containerStyleType.equals(ContainerStyleType.Chess))
			concepts.set(Concept.ChessStyle.id(), true);
		else if (containerStyleType.equals(ContainerStyleType.Go))
			concepts.set(Concept.GoStyle.id(), true);
		else if (containerStyleType.equals(ContainerStyleType.Mancala))
		{
			concepts.set(Concept.MancalaStyle.id(), true);
			concepts.set(Concept.MancalaBoard.id(), true);
			if (game.board() instanceof MancalaBoard)
			{
				final MancalaBoard mancalaBoard = (MancalaBoard) game.board();
				final int numRows = mancalaBoard.numRows();
				final StoreType storeType = mancalaBoard.storeType();

				if (!storeType.equals(StoreType.None))
					concepts.set(Concept.MancalaStores.id(), true);

				if (numRows == 2)
					concepts.set(Concept.MancalaTwoRows.id(), true);
				else if (numRows == 3)
					concepts.set(Concept.MancalaThreeRows.id(), true);
				else if (numRows == 4)
					concepts.set(Concept.MancalaFourRows.id(), true);
				else if (numRows == 6)
					concepts.set(Concept.MancalaSixRows.id(), true);

			}
			else
			{
				final boolean circleTiling = game.booleanConcepts().get(Concept.CircleTiling.id());
				if (circleTiling)
					concepts.set(Concept.MancalaCircular.id(), true);
			}
		}
		else if (containerStyleType.equals(ContainerStyleType.PenAndPaper))
			concepts.set(Concept.PenAndPaperStyle.id(), true);
		else if (containerStyleType.equals(ContainerStyleType.Shibumi))
			concepts.set(Concept.ShibumiStyle.id(), true);
		else if (containerStyleType.equals(ContainerStyleType.Backgammon))
			concepts.set(Concept.BackgammonStyle.id(), true);
		else if (containerStyleType.equals(ContainerStyleType.Janggi))
			concepts.set(Concept.JanggiStyle.id(), true);
		else if (containerStyleType.equals(ContainerStyleType.Xiangqi))
			concepts.set(Concept.XiangqiStyle.id(), true);
		else if (containerStyleType.equals(ContainerStyleType.Shogi))
			concepts.set(Concept.ShogiStyle.id(), true);
		else if (containerStyleType.equals(ContainerStyleType.Table))
			concepts.set(Concept.TableStyle.id(), true);
		else if (containerStyleType.equals(ContainerStyleType.Surakarta))
			concepts.set(Concept.SurakartaStyle.id(), true);
		else if (containerStyleType.equals(ContainerStyleType.Tafl))
			concepts.set(Concept.TaflStyle.id(), true);
		else if (containerStyleType.equals(ContainerStyleType.Graph))
			concepts.set(Concept.GraphStyle.id(), true);

		return concepts;
	}

	@Override
	public long gameFlags(final Game game)
	{
		final long gameFlags = 0l;
		return gameFlags;
	}

	@Override
	public boolean needRedraw()
	{
		return false;
	}

}

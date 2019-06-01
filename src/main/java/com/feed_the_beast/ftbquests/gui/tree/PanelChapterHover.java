package com.feed_the_beast.ftbquests.gui.tree;

import com.feed_the_beast.ftblib.lib.gui.Panel;
import com.feed_the_beast.ftblib.lib.gui.Theme;
import com.feed_the_beast.ftblib.lib.gui.Widget;
import com.feed_the_beast.ftblib.lib.gui.WidgetLayout;
import com.feed_the_beast.ftbquests.quest.QuestChapter;
import net.minecraft.client.renderer.GlStateManager;

/**
 * @author LatvianModder
 */
public class PanelChapterHover extends Panel
{
	public final GuiQuestTree treeGui;
	public ButtonChapter chapter = null;
	public int type = -1;

	public PanelChapterHover(Panel panel)
	{
		super(panel);
		treeGui = (GuiQuestTree) panel.getGui();
		setPosAndSize(-1, -1, 0, 0);
	}

	@Override
	public void addWidgets()
	{
		if (chapter != null)
		{
			type = 0;

			for (QuestChapter c : treeGui.file.chapters)
			{
				if (c.group == chapter.chapter)
				{
					if (widgets.isEmpty())
					{
						type = 1;
						add(new ButtonExpandedChapter(this, chapter.chapter));
					}

					ButtonExpandedChapter b = new ButtonExpandedChapter(this, c);
					b.setX(20);
					add(b);
				}
			}

			if (type == 0)
			{
				add(new ButtonExpandedChapter(this, chapter.chapter));
			}

			setHeight(align(WidgetLayout.VERTICAL));
			setWidth(getContentWidth());

			for (Widget widget : widgets)
			{
				widget.setWidth(width - widget.posX);
			}

			setPos(chapter.getX(), Math.min(chapter.getY() - 1, treeGui.height - height - 1));
		}
		else
		{
			type = -1;
			setPosAndSize(-1, -1, 0, 0);
		}
	}

	@Override
	public void alignWidgets()
	{
	}

	@Override
	public void drawBackground(Theme theme, int x, int y, int w, int h)
	{
	}

	@Override
	public void draw(Theme theme, int x, int y, int w, int h)
	{
		if (type != -1)
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(0F, 0F, 500F);
			super.draw(theme, x, y, w, h);
			GlStateManager.popMatrix();
		}
	}

	@Override
	public void tick()
	{
		if (!isMouseOverAnyWidget())
		{
			ButtonChapter c = null;

			for (Widget widget : treeGui.chapterPanel.widgets)
			{
				if (widget instanceof ButtonChapter && widget.isMouseOver())
				{
					c = (ButtonChapter) widget;
				}
			}

			if (chapter != c)
			{
				chapter = c;
				refreshWidgets();
			}
		}

		super.tick();
	}
}
package de.uniks.jism.gui.theme;

/*
java-syntax-highlighter
Copyright (c) 2011 Chan Wai Shing

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

import org.sdmlib.serialization.Style;

/**
 * Midnight theme.
 * @author Chan Wai Shing <cws1989@gmail.com>
 */
public class ThemeMidnight extends Theme {
public ThemeMidnight() {
//	    setGutterText(Color.decode("0xafafaf"));
//	    setGutterBorderColor(Color.decode("0x435a5f"));
//	    setGutterBorderWidth(3);
//	    setGutterTextFont(new Font("Verdana", Font.PLAIN, 11));
//	    setGutterTextPaddingLeft(7);
//	    setGutterTextPaddingRight(7);
    // Midnight SyntaxHighlighter theme based on theme by J.D. Myers
    // http://webdesign.lsnjd.com/
    addStyle(StyleConstants.STYLE_PLAIN, new Style(Style.PROPERTY_FONTFAMILY, "Consolas",
			Style.PROPERTY_FONTSIZE, 12,
			Style.PROPERTY_BACKGROUND, "0f192a",
			Style.PROPERTY_FORGROUND, "d1edff"));
	addStyle(StyleConstants.STYLE_HIGHTLIGHTED, new Style(Style.PROPERTY_BACKGROUND, "253e5a"));
	addStyle(StyleConstants.STYLE_BOLD, new Style(Style.PROPERTY_BOLD, true));
	addStyle(StyleConstants.STYLE_COMMENTS, new Style(Style.PROPERTY_FORGROUND, "428bdd"));
	addStyle(StyleConstants.STYLE_STRING, new Style(Style.PROPERTY_FORGROUND, "1dc116"));
	addStyle(StyleConstants.STYLE_PREPROCESSOR, new Style(Style.PROPERTY_FORGROUND, "8aa6c1"));
	addStyle(StyleConstants.STYLE_VARIABLE, new Style(Style.PROPERTY_FORGROUND, "ffaa3e"));
	addStyle(StyleConstants.STYLE_VALUE, new Style(Style.PROPERTY_FORGROUND, "f7e741"));
	addStyle(StyleConstants.STYLE_FUNCTIONS, new Style(Style.PROPERTY_FORGROUND, "ffaa3e"));
	addStyle(StyleConstants.STYLE_CONSTANTS, new Style(Style.PROPERTY_FORGROUND, "e0e8ff"));
	addStyle(StyleConstants.STYLE_SCRIPT, new Style(Style.PROPERTY_FORGROUND, "b43d3d", Style.PROPERTY_BOLD, true));
	addStyle(StyleConstants.STYLE_COLOR1, new Style(Style.PROPERTY_FORGROUND, "f8bb00"));
	addStyle(StyleConstants.STYLE_COLOR2, new Style(Style.PROPERTY_FORGROUND, "FFFFFF"));
	addStyle(StyleConstants.STYLE_COLOR3, new Style(Style.PROPERTY_FORGROUND, "0ffaa3e"));
  }
}

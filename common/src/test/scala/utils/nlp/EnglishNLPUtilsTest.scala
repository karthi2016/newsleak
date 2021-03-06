/*
 * Copyright (C) 2015  Language Technology Group
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package utils.nlp

import testFactories.FlatSpecWithCommonTraits

class EnglishNLPUtilsTest extends FlatSpecWithCommonTraits {

  // We won't test the tokenize method, because it doesn't add any further
  // functions and is already tested by ScalaNLP.
  val uut = new EnglishNLPUtils()

  "segmentation" should "split multiple sentences at sentence boundaries" in {
    val s1 = "Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live!"
    val s2 = "Deleted code is debugged code."
    val s3 = "Is this a question?"
    val s4 = "The end."

    val expected = Vector(s1, s2, s3, s4)
    val text = expected.mkString(" ")

    val actual = uut.segmentText(text)

    assert(expected == actual)
  }

  "segmentation" should "remove newlines and trim source text" in {
    val text =
      """ First line
          second line?


          last line.
      """

    val expected = Vector("First line           second line?", "last line.")
    val actual = uut.segmentText(text)

    assert(expected == actual)
  }
}

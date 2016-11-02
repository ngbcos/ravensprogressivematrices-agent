# ravensprogressivematrices-agent

##About the project
This project was created as a part of the Knowledge-Based Artificial Intelligence course offered by Georgia Tech through their online master's program in Computer Science. 

##What are Raven's Progressive Matrices?
There are numerous tests of human intelligence, but one of the most reliable and commonly-used is Raven’s Progressive Matrices. Raven’s Progressive Matrices, or RPM, are visual analogy problems where the test-taker is given a matrix of figures and asked to select the figure that completes the matrix. Examples of 2x2 and 3x3 RPM-style problems are shown below.

![2x2 RPM](https://github.com/arwarner/ravensprogressivematrices-agent/raw/master/RPMAgent/Problems/Basic Problems B/Basic Problem B-01/Basic Problem B-01.PNG)

![3x3 RPM Problem](https://github.com/arwarner/ravensprogressivematrices-agent/raw/master/RPMAgent/Problems/Basic Problems E/Basic Problem E-09/Basic Problem E-09.PNG)

##What does this agent do?
The intention behind the project is to create an agent that can read in RPM problems as images, and use pattern matching to generate solutions. The goal of this project is to authentically experience the overall goals of knowledge-based AI: to design an agent with human-like, human-level intelligence; to test that agent against a set of authentic problems; and to use that agent’s performance to reflect on what we believe about human cognition.

##How does it work?
The agent makes use of frames, classification, and logic to choose the most likely solution for each problem from six to eight possible answer choices. The first step of my agent is to load all images into a frame, represented by an object called “ImageInfo”. This frame consists of 5 fields – the name, the BufferedImage object itself, the number of black pixels in the image, the
number of total pixels in the image, and the ratio of pixels in the image that are black. The latter three points of information are determined as the frame is being created, by iterating through all points in the image and counting those that are black.

Once the frame has been created, it is compared against quite a few different generic classes of problems. I determined these by looking at all of the basic Raven’s problems provided and sorting them into general categories. By describing what defines a certain category, the agent can use the image frames created in the previous step to determine what sort of problem it is dealing with. Once it has categorized the problem type, it is easier for the agent to then know what steps to take to solve the problem. The categories I've identified are below:

###Equality

![EqualRPM](https://github.com/arwarner/ravensprogressivematrices-agent/raw/master/RPMAgent/Problems/Basic Problems E/Basic Problem E-09/Basic Problem E-09.PNG)

Defined by all shapes within it being equal. The agent determines if a RPM problem fits into the equality category by comparing the number of black pixels in each image – if they’re all equal (with a small allowance provided for error), then it is an equality relationship. The agent can then find the answer by finding the answer choice that can be overlaid with all other images in the problem and leave no pixels remaining unmatched.

###One-Way Same
![OnewaySameRPM](https://github.com/arwarner/ravensprogressivematrices-agent/raw/master/RPMAgent/Problems/Basic Problems C/Basic Problem C-01/Basic Problem C-01.PNG)

A relationship where either all columns or all rows are equal, but not both. So, if the horizontal rows are all made up of the same shapes, as pictured above, but change within vertical columns, the RPM fits within this class. There are 4 subclasses that further refine this categorization – One-Way Vertical Gain (as pictured above), One-Way Horizontal Gain, One-Way Vertical Loss, and One-Way Horizontal Loss. The answer is chosen based on the subcategory. For instance, if the subcategory was One-Way Vertical Loss, then the agent knows that the shapes contain less pixels the farther down the vertical columns it goes. So it would pick the answer choice that was smaller than all other shapes in its column, and matched the shapes in its row. The algorithm is the same for all subcategories within this class, with the exception that some will grow and some will shrink, and the direction of change. 

###Radial

![RadialRPM](https://github.com/arwarner/ravensprogressivematrices-agent/raw/master/RPMAgent/Problems/Basic Problems C/Basic Problem C-07/Basic Problem C-07.PNG)


Consists of a ring of equal-weighted cells surrounding a smaller-weighted cell in the center. The agent identifies this pattern by ensuring the first row and column are all equal, but the second row and column have a dip in black pixel count in the center due to the smaller inner cell. This can be solved by finding a cell that is a mirror image of the bottom left cell.  

###Gain and Loss

![GainLossRPM](https://github.com/arwarner/ravensprogressivematrices-agent/raw/master/RPMAgent/Problems/Basic Problems C/Basic Problem C-11/Basic Problem C-11.PNG)


Consists of all problems where the cell count is growing across each horizontal row, but shrinking down each vertical column, or vice versa. It can be found by examining each row and column individually – if both columns tend to lose black pixels but both rows tend to gain, or vice versa, then the agent knows the RPM fits this category. It solves this problem by finding the cell whose weight satisfies both the growing and shrinking constraints proportional to the rate at which the rest of the cells are growing and shrinking. 

###Rotation

![RotationRPM](https://github.com/arwarner/ravensprogressivematrices-agent/raw/master/RPMAgent/Problems/Basic Problems D/Basic Problem D-02/Basic Problem D-02.PNG)


A rotational relationship is one where a shape moves both horizontally and vertically across rows. If the shape moves down and to the right, this is a right rotation, as seen above. The circle moves from the (0,0) position to the (1,1) position – both down and to the right. Shapes can also move down and to the left – a left rotation. The agent finds the answer to this sort of problem by overlaying each shape with its corresponding predecessor according to the above pattern, and choses the closest match. Other subsets of this classification include the partial rotation, where only one shape of  many rotates, or two shapes within an image rotate in opposing directions. The agent is able to recognize these partial rotation patterns as well, and classify them in a similar way. Instead of looking for all pixels to match, though, it looks for a constant ratio of pixels in common in each image. 

###Additive

![AdditiveRPM](https://github.com/arwarner/ravensprogressivematrices-agent/raw/master/RPMAgent/Problems/Basic Problems E/Basic Problem E-01/Basic Problem E-01.PNG)

Represents a straightforward mathematical relationship – the contents of two cells within a row or column add up to produce the third. The resulting shape can either be on the end as seen above or in the center. The agent identifies this by overlaying two images and then seeing if the resulting image matches the third. To find the answer, if the problem does fit this classification, the agent will simply test each answer choice in the same equation described above and see if it is able to yield the correct result. 

###Subtractive

![SubtractiveRPM]https://github.com/arwarner/ravensprogressivematrices-agent/raw/master/RPMAgent/Problems/Basic Problems E/Basic Problem E-05/Basic Problem E-05.PNG)

Identical to the previous, only with the mathematical operation being subtraction instead of addition.

###AND & XOR

![ANDXORRPM](https://github.com/arwarner/ravensprogressivematrices-agent/raw/master/RPMAgent/Problems/Basic Problems E/Basic Problem E-07/Basic Problem E-07.PNG)

Two images are overlaid, and only the pixels that they have in common are drawn onto the resulting image. If this image matches the third in the row or column, and this trend continues for all rows and columns, then the RPM can be said to have an “AND” relationship, named for the logical operation it represents. Similarly, when the images are overlaid and only those pixels which appear in one but not both are added to the resulting image, this can be said to represent the XOR relationship, as pictured above. Once it has been determined that a relationship represents and AND or XOR, the agent can do the same process described above on all answers in order to find the closest fit. 


##What's next?
One of the biggest weaknesses of my project is that it does not have the ability to identify shapes. This becomes a problem in relationships such as the one seen in the AND and XOR section above. My agent would categorize this problem into the XOR classification. But when it examines the leftmost column, it would expect to see the following in position G: 

![Expected G](https://github.com/arwarner/ravensprogressivematrices-agent/raw/master/RPMAgent/src/images/expected.png)

This is because my agent is doing the XOR action based on pixels, while the original RPM is intended to be operated on as if it were independent shapes. So while the cross and the circle do overlap, they don’t count as appearing in both as those whole shapes only appear in one each. Though my agent does have a great bit of room for error, the difference in pixel count between these two is so great that my agent is not able to recognize this as a possible solution. I attempted to create outlines around each shape and use these for the XOR operation to bypass this problem, however, that created more issues than it solved (for instance, since many of these shapes don’t line up perfectly, shapes that in theory should match can have 1 pixel wide borders that don’t touch at a single point) and I ultimately decided to scrap the idea in order to preserve other functional parts of the agent. This issue also appears in problems where a smaller shape, such as a huge, unfilled circle, is utterly engulfed by another shape, such as a large, filled square. The majority of the pixels can be attributed to this square, so any changes in the circle will probably be within the margin of error for the changes that the square may be undergoing. This can be an issue in the problems like the one seen below.  Again, the ability to identify shapes would right this problem, but my agent is not advanced enough to do so, but I would like to do so in the future 


Another item I hope to implement in this project is an element of learning. Since this course was focused around artificial intelligence, allowing the agent to learn was not emphasized. Because of this, the agent can’t really be said to be the one learning – I learned the categorizations, and taught them to my agent. The closest it can be said to come to mimicking human cognition is using what it has been taught to categorize new problems, in a similar fashion to a student who is given a list of formulas to memorize by their math teacher and then uses those formulas to solve new problems they are given. They didn’t derive any of those formulas themselves, and the knowledge is the result of memorization, not understanding - but nonetheless, the student can do the work required of them now. 

However, the agent does have the ability to check its answer and get the correct one, so it would be neat to adjust the agent to check it's answers as it goes, and learn from mistakes. In order for a learning technique to be really successful, though, it would need many sets to train over, which were not provided as a part of the course either. I'm hopeful I can find enough external data to train over, though, and hope to implement this at some point in the future. 



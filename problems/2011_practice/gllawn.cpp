#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <GL/glut.h>

struct Point
{
    double x, y;
};

FILE *fp;
Point lot[16], house[16], bed[16][16];
double minX, maxX, minY, maxY;
int lotCount, houseCount, bedCount[16];
int numBeds, numVerts;
int winWidth, winHeight;

long ccw(Point p0, Point p1, Point p2)
{
    double dx1, dy1, dx2, dy2;

    dx1 = p1.x - p0.x;
    dy1 = p1.y - p0.y;
    dx2 = p2.x - p0.x;
    dy2 = p2.y - p0.y;

    return (long)(dx1*dy2 - dx2*dy1);
}

int within(double B, double A, double C)
{
    if (A > C)
        return ((B >= C) && (B <= A));
    else
        return ((B >= A) && (B <= C));
}

int segmentIntersect(Point l1, Point l2, Point m1, Point m2)
{
    double ldx, ldy, mdx, mdy;
    double P, Q, D;

    ldx = l2.x - l1.x;
    ldy = l2.y - l1.y;
    mdx = m2.x - m1.x;
    mdy = m2.y - m1.y;

    D = ldx * -mdy + ldy * mdx;
    P = (m1.x - l1.x) * -mdy + (m1.y-l1.y) * mdx;
    Q = ldx * (m1.y - l1.y) - ldy * (m1.x - l1.x);

    if (!D)
    {
        if (!Q)
            return (within(m1.x, l1.x, l2.x) && within(m1.y, l1.y, l2.y)) ||
                   (within(m2.x, l1.x, l2.x) && within(m2.y, l1.y, l2.y)) ||
                   (within(l1.x, m1.x, m2.x) && within(l1.y, m1.y, m2.y)) ||
                   (within(l2.x, m1.x, m2.x) && within(l2.y, m1.y, m2.y));
        else
            return 0;
    }
    else
        return (within(P, 0, D) && within(Q, 0, D));
}


void triangulate(Point poly[], int size, float r, float g, float b)
{
    int p0, p1, p2, p3;
    int loop, next, flag, n;

printf("triangulating polygon, size = %d\n", size);
for (n = 0; n < size; n++)
    printf("   %0.2lf, %0.2lf\n",  poly[n].x, poly[n].y);

    n = size;
    p0 = -1;
    while (n > 2) // (n > 3) if you want only diagonals
    {
        p0 = (p0 + 1) % n;
        p1 = (p0 + 1) % n;
        p2 = (p0 + 2) % n;
        p3 = (p0 + 3) % n;
printf("ccw(<%0.2lf,%0.2lf>,<%0.2lf,%0.2lf>,<%0.2lf,%0.2lf>) = %d\n",
    poly[p2].x,poly[p2].y,poly[p3].x,poly[p3].y,poly[p1].x,poly[p1].y,
    ccw(poly[p2], poly[p3], poly[p1]));
        if (ccw(poly[p2], poly[p3], poly[p1]) >= 0)
        {
            if ((n > 3) && 
                           (ccw(poly[p2], poly[p1], poly[p0]) < 0) &&
                           (ccw(poly[p3], poly[p0], poly[p1]) <= 0))
            {
printf("  ccw(<%0.2lf,%0.2lf>,<%0.2lf,%0.2lf>,<%0.2lf,%0.2lf>) = %d\n",
    poly[p2].x,poly[p2].y,poly[p1].x,poly[p1].y,poly[p0].x,poly[p0].y,
    ccw(poly[p2], poly[p1], poly[p0]));
printf("  ccw(<%0.2lf,%0.2lf>,<%0.2lf,%0.2lf>,<%0.2lf,%0.2lf>) = %d\n",
    poly[p3].x,poly[p3].y,poly[p0].x,poly[p0].y,poly[p1].x,poly[p1].y,
    ccw(poly[p3], poly[p0], poly[p1]));
                continue;
            }

            flag = 0;
            if (n > 4)
            {
                for (loop = (p3+1) % n; loop != p0; loop = (loop+1) % n)
                {
                    next = (loop+1) % n;
                    if (segmentIntersect(poly[p1], poly[p3], poly[loop], poly[next]))
                    {
printf("isect failed!********\n");
                        flag = 1;
                        break;
                    }
                }
            }

            if (flag == 0)
            {
                // p1, p2, and p3 form a triangle; p1 and p3 are the endpoints
                //  of the diagonal. If any processing needs to be done for each
                //  found triangle, do it here.
                // * Begin optional code *
                printf("Triangle at (%0.2lf,%0.2lf)-(%0.2lf,%0.2lf)-(%0.2lf,%0.2lf)\n", poly[p1].x, poly[p1].y, poly[p2].x, poly[p2].y, poly[p3].x, poly[p3].y);
                glColor4f(r, g, b, 1.0);
                glPolygonMode(GL_FRONT, GL_FILL);
                glPolygonMode(GL_BACK, GL_FILL);
                glBegin(GL_TRIANGLES);
                    glVertex2d(poly[p1].x, poly[p1].y);
                    glVertex2d(poly[p2].x, poly[p2].y);
                    glVertex2d(poly[p3].x, poly[p3].y);
                glEnd();
                glColor4f(1.0, 1.0, 1.0, 1.0);
                glPolygonMode(GL_FRONT, GL_LINE);
                glPolygonMode(GL_BACK, GL_LINE);
                glBegin(GL_TRIANGLES);
                    glVertex2d(poly[p1].x, poly[p1].y);
                    glVertex2d(poly[p2].x, poly[p2].y);
                    glVertex2d(poly[p3].x, poly[p3].y);
                glEnd();
                // * End optional code *
                memmove(&(poly[p2]), &(poly[p2+1]), sizeof(Point) * (n - p2 - 1));
                n--;
            }
        }
    } // while n > 2
} // Triangulate


void keypress(unsigned char key, int x, int y)
{
    char line[256];
    int i, j;

    minX = 1000000; maxX = -1000000;
    minY = 1000000; maxY = -1000000;

    fgets(line, sizeof(line), fp);
    lotCount = atoi(strtok(line, " \n"));
    if (lotCount != 0)
    {
        // Lot
        for (i = 0; i < lotCount; i++)
        {
            lot[i].x = atof(strtok(NULL, " \n"));
            lot[i].y = atof(strtok(NULL, " \n"));

            if (lot[i].x < minX)
                minX = lot[i].x;
            if (lot[i].y < minY)
                minY = lot[i].y;
            if (lot[i].x > maxX)
                maxX = lot[i].x;
            if (lot[i].y > maxY)
                maxY = lot[i].y;
        }

        // House
        fgets(line, sizeof(line), fp);
        houseCount = atoi(strtok(line, " \n"));
        for (i = 0; i < houseCount; i++)
        {
            house[i].x = atof(strtok(NULL, " \n"));
            house[i].y = atof(strtok(NULL, " \n"));
        }

        // Beds
        fgets(line, sizeof(line), fp);
        numBeds = atoi(strtok(line, " \n"));
        for (i = 0; i < numBeds; i++)
        {
            fgets(line, sizeof(line), fp);
            bedCount[i] = atoi(strtok(line, " \n"));
            glBegin(GL_POLYGON);
                for (j = 0; j < bedCount[i]; j++)
                {
                    bed[i][j].x = atof(strtok(NULL, " \n"));
                    bed[i][j].y = atof(strtok(NULL, " \n"));
                }
            glEnd();
        }
    }
    else
    {
        fclose(fp);
        exit(0);
    }

    glutPostRedisplay();
}

void reshape(int width, int height)
{
    double windowAspect, lotAspect;
    double left, right, bottom, top;

    winWidth = width;
    winHeight = height;

    if (height <= 0)
        return;

    if ((maxY - minY) <= 0)
        return;

    windowAspect = width / height;
    lotAspect = (maxX - minX) / (maxY - minY);

    if (lotAspect > 0)
    {
        left = minX - 10.0;
        right = maxX + 10.0;
        bottom = minY - 10.0;
        top = bottom + (right - left) / windowAspect;
    }
    else
    {
        bottom = minY - 10.0;
        top = maxY + 10.0;
        left = minX - 10.0;
        right = left + (top - bottom) / windowAspect;
    }

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluOrtho2D(left, right, bottom, top);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
}

void display()
{
    int i, j;
    Point poly[64];

    reshape(winWidth, winHeight);

    glClear(GL_COLOR_BUFFER_BIT);

    // Lot
    memcpy(poly, lot, sizeof(Point) * lotCount);
    triangulate(poly, lotCount, 0.0, 1.0, 0.0);

    // House
    memcpy(poly, house, sizeof(Point) * houseCount);
    triangulate(poly, houseCount, 0.0, 0.0, 1.0);

    // Beds
    for (i = 0; i < numBeds; i++)
    {
        memcpy(poly, bed[i], sizeof(Point) * bedCount[i]);
        triangulate(poly, bedCount[i], 0.5, 0.3, 0.3);
    }

    glutSwapBuffers();
}

void init()
{
    glDisable(GL_LIGHTING);
    glDisable(GL_DEPTH_TEST);
    glDisable(GL_CULL_FACE);
    //glEnable(GL_BLEND);
    //glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glClearColor(0.0, 0.0, 0.0, 0.0);
}

int main(int argc, char *argv[])
{
    char line[80];
    double area;

    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGBA | GLUT_DOUBLE);

    fp = fopen("lawn.in", "r");

    glutInitWindowPosition(160, 100);
    glutInitWindowSize(800, 600);
    winWidth = 800; winHeight = 600;
    glutCreateWindow("Lawn Visualization");

    glutDisplayFunc(display);
    glutReshapeFunc(reshape);
    glutKeyboardFunc(keypress);

    init();
    keypress(' ', 0, 0);

    glutMainLoop();
}


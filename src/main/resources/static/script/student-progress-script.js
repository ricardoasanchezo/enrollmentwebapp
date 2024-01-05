window.onload = async function buildList()
{
    let url = domain + "/api/student/get";

    const studentResponse = await fetch(url);
    let student = await studentResponse.json();

    let approvedCourseCodes = student.approvedCourses.map(course => {return course.code});
    let majorCourseNodes = student.major.courseNodes;

    document.getElementById("student-id").innerText = student.id;
    document.getElementById("major-name").innerText = student.major.name;
    document.getElementById("major-dist-credits").innerText = student.major.distCredits;
    document.getElementById("major-elect-credits").innerText = student.major.electCredits;
    document.getElementById("major-dist-credits-inline").innerText = student.major.distCredits;

    let courseCardMap = new Map();

    majorCourseNodes.forEach(courseNode =>
    {
        let code = courseNode.course.code;
        let title= courseNode.course.title;
        let credits = courseNode.course.credits;

        let state;
        if (approvedCourseCodes.includes(code))
        {
            state = 'APPROVED';
        }
        else if (
            // approved courses contains all hard requirements
            courseNode.hardReqs.every(req => approvedCourseCodes.includes(req.code)) &&
            // approved courses contains all soft requirements
            courseNode.softReqs.some(req => approvedCourseCodes.includes(req.code)) ||
            // course node has no requirements
            courseNode.hardReqs.length === 0 && courseNode.softReqs.length === 0
        )
        {
            state = 'PENDING';
        }
        else
        {
            state = 'BLOCKED';
        }

        let isDistCourse = courseNode.isDistCourse;

        let courseCard = new CourseCard(code, title, credits, state, isDistCourse);

        courseCardMap.set(code, courseCard);
    });

    // Filter courses
    let majorCode = student.major.code;
    let filters = courseFilterByMajor[majorCode];

    filters.forEach(codeList =>
    {
        codeList.forEach(code =>
        {
            if (courseCardMap.get(code) !== undefined)
            {
                let list = [''];
                list.slice()
            }
        });
    });


    for (const courseCardMapElement of courseCardMap)
    {
        let card = courseCardMapElement[1];

        let id = `${card.isDistCourse ? 'dist' : 'main'}-${card.state.icon}`

        document.getElementById(id).appendChild(card);
    }
}

const courseFilterByMajor = {
    '120-2019': [
        ['GEPE3010', 'GEPE3020', 'GEPE3030'],
        ['GEHS3020', 'GEHS3050', 'GEHS4020', 'GEHS4030']
    ]
}
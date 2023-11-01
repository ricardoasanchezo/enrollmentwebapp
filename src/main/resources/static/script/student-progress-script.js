const state = {
    APPROVED: "done",
    BLOCKED: "close",
    PENDING: "pending"
}

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

    majorCourseNodes.forEach(courseNode =>
    {
        let code = courseNode.course.code;
        let title= courseNode.course.title;
        let credits = courseNode.course.credits;

        let courseState;
        if (approvedCourseCodes.includes(code))
        {
            courseState = state.APPROVED;
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
            courseState = state.PENDING;
        }
        else
        {
            courseState = state.BLOCKED;
        }

        let htmlCourseCard = document.getElementById("course-card-template").cloneNode(true);
        htmlCourseCard.removeAttribute("id");

        htmlCourseCard.children[0].children[0].innerText = courseState;
        htmlCourseCard.children[1].children[0].innerText = code;
        htmlCourseCard.children[1].children[1].innerText = title;
        htmlCourseCard.children[2].innerText = credits;

        htmlCourseCard.classList.add(courseState);

        if (courseNode.isDistCourse)
        {
            document.getElementById("dist-" + courseState).appendChild(htmlCourseCard);
        }
        else
        {
            document.getElementById("main-" + courseState).appendChild(htmlCourseCard);
        }
    });
}

// <div id="course-card-template" className="course-card">
//     <div className="course-icon" style="background-color: green">
//         <span className="material-icons">done</span>
//     </div>
//     <div>
//         <div th:text="${course.getCode()}" className="course-code"></div>
//         <div th:text="${course.getTitle()}" className="course-name"></div>
//     </div>
//     <span th:text="${course.getCreditCount()}" className="course-credit"></span>
// </div>

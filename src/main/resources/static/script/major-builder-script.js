//===================== POPULATE MAJOR INFO ==============================================================================

let courseNodeContainer;
let updateButton;

window.onload = async function()
{
    const allCodesResponse = await fetch(domain + "/api/courses/getAllCodes")
    const allCodes = await allCodesResponse.json();

    let pathMajorCode = window.location.pathname.split('/').slice(-1);

    const majorResponse = await fetch(domain + "/api/major/" + pathMajorCode);
    let major = await majorResponse.json();

    courseNodeContainer = new CourseNodeContainer(allCodes, major);
    document.getElementById("content").appendChild(courseNodeContainer);



    // document.getElementById("major-code").innerText = major.code;
    // document.getElementById("major-name").value = major.name;
    // document.getElementById("major-dist-credits").value = major.distCredits;
    // document.getElementById("major-elect-credits").value = major.electCredits;

    // let courseNodes = major.courseNodes;
    //
    // courseNodes.sort(function(a, b) { return a.nodeIndex - b.nodeIndex });
    //
    // for (let i = 0; i < courseNodes.length; i++)
    // {
    //     let courseNode = new CourseNode(
    //         courseNodeContainer,
    //         courseNodes[i].code,
    //         courseNodes[i].passingGrade,
    //         courseNodes[i].hardReqs,
    //         courseNodes[i].softReqs,
    //         courseNodes[i].specialReqs,
    //         courseNodes[i].isDistCourse,
    //         i
    //     );
    //
    //     courseNodeContainer.addCourseNode(courseNode)
    // }
}


//===================== UPDATE MAJOR ==============================================================================

// async function updateMajor()
// {
//     let updateRequest = {
//             "code": document.getElementById("major-code").innerText,
//             "name": document.getElementById("major-name").value,
//             "courseNodes": courseNodeContainer.getDtoList(),
//             "distCredits": parseInt(document.getElementById("major-dist-credits").value),
//             "electCredits": parseInt(document.getElementById("major-elect-credits").value)
//     }
//
//     let url = domain + "/api/admin/major/" + updateRequest.code + "/update"
//
//     await fetch(url, {
//         method: 'PUT',
//         headers: { 'Content-Type': 'application/json' },
//         body: JSON.stringify(updateRequest)
//     })
//         .then(response => alert("Success updating major"))
//         .catch(error => alert("Failed to update major"))
//
// }

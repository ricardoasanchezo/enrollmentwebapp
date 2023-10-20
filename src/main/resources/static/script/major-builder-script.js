let allCourseCodes;
let courseNodeList;
let currentCourseCodes = [];
let offset = 2; // Number of elements before course input in course node

class CourseNode
{
    constructor(courseCode, hardReqsCodes, softReqsCodes, specialReqs, isDistCourse, index)
    {
        this.courseCode = courseCode;
        this.hardReqsCodes = hardReqsCodes;
        this.softReqsCodes = softReqsCodes;
        this.specialReqs = specialReqs;
        this.isDistCourse = isDistCourse;
        this.index = index;
    }
}

function checkCourseCodeMatch(element)
{
    let isValid = allCourseCodes.includes(element.value);

    let isEmpty = (element.value == null || element.value === "");

    toggleStyleOnInput(element, isValid, isEmpty);
}

function checkCourseCodeRepeat(element, codeList)
{
    let isValid = !codeList.includes(element.value);

    toggleStyleOnInput(element, isValid, false);
}

function checkCourseCode(element, codeList)
{
    let isValid = allCourseCodes.includes(element.value) && !codeList.includes(element.value);

    let isEmpty = (element.value == null || element.value === "");

    toggleStyleOnInput(element, isValid, isEmpty);
}

function checkHardRequirementsMatch(element)
{
    let reqs= element.value.split(",").map(code => { return code.trim(); })

    let isEmpty = element.value == null || element.value === "";

    let isValid= reqs.every(req => currentCourseCodes.includes(req));

    toggleStyleOnInput(element, isValid, isEmpty);
}

function checkSoftRequirementsMatch(element)
{
    let reqs= element.value.split(",").map(code => { return code.trim(); })

    let isEmpty = element.value == null || element.value === "";

    let isValid=
        reqs.length > 1 &&
        reqs.some(req => currentCourseCodes.includes(req)) &&
        reqs.every(req => allCourseCodes.includes(req))
    ;

    toggleStyleOnInput(element, isValid, isEmpty);
}

function toggleStyleOnInput(element, isValid, isEmpty)
{
    element.style.color = (isValid) ? "darkgreen" : "red";
    element.style.backgroundColor = (isValid) ? "lightgreen" : "lightpink";

    if (isEmpty)
    {
        element.style.color = "black";
        element.style.backgroundColor = "white";
    }
}

function updateCourseNodeList()
{
    let htmlNodeList = document.getElementById("node-list").children;
    courseNodeList = [];
    currentCourseCodes = [];

    for (let i = 1; i < htmlNodeList.length; i++)
    {
        let htmlNode = htmlNodeList[i].children;

        // checkCourseCodeMatch(htmlNode[offset]);
        // checkCourseCodeRepeat(htmlNode[offset], currentCourseCodes);

        checkCourseCode(htmlNode[offset], currentCourseCodes);

        if (allCourseCodes.includes(htmlNode[offset].value))
            currentCourseCodes.push(htmlNode[offset].value);

        checkHardRequirementsMatch(htmlNode[1 + offset]);
        checkSoftRequirementsMatch(htmlNode[2 + offset]);

        let courseNode = new CourseNode(
            htmlNode[offset].value,
            htmlNode[1 + offset].value.split(",").map(code => { return code.trim(); }),
            htmlNode[2 + offset].value.split(",").map(code => { return code.trim(); }),
            htmlNode[3 + offset].value,
            htmlNode[4 + offset].checked,
            i
        )

        courseNodeList.push(courseNode);
    }
}

function addNewCourseNode()
{
    let newNode = document.getElementById("node-template").cloneNode(true);
    newNode.removeAttribute("id");

    newNode.addEventListener("dragstart",()=>
    {
        newNode.classList.add("dragging");
    });
    newNode.addEventListener("dragend",()=>
    {
        newNode.classList.remove("dragging");
        updateCourseNodeList();
    });

    document.getElementById("node-list").appendChild(newNode);
}

function removeCourseNode(button)
{
    button.parentElement.remove();
    updateCourseNodeList();
}

function autoGrow(element)
{
    element.style.height = "5px";
    element.style.height = (element.scrollHeight) + "px";
}

//===================== POPULATE MAJOR INFO ==============================================================================

window.onload = async function()
{
    const codesResponse = await fetch(domain + "/api/courses/getAllCodes");
    allCourseCodes = await codesResponse.json();

    let pathMajorCode = window.location.pathname.split('/').slice(-1);

    const majorResponse = await fetch(domain + "/api/major/" + pathMajorCode);
    let major = await majorResponse.json();

    document.getElementById("major-code").innerText = major.code;
    document.getElementById("major-name").value = major.name;
    document.getElementById("major-dist-credits").value = major.distCredits;
    document.getElementById("major-elect-credits").value = major.electCredits;

    let courseNodes = major.courseNodes;

    courseNodes.sort(function(a, b){return a.nodeIndex - b.nodeIndex});

    for (let i = 0; i < courseNodes.length; i++)
    {
        let newNode = document.getElementById("node-template").cloneNode(true);
        newNode.removeAttribute("id");

        newNode.addEventListener("dragstart",()=>
        {
            newNode.classList.add('dragging');
        });
        newNode.addEventListener("dragend",()=>
        {
            newNode.classList.remove('dragging');
            updateCourseNodeList();
        });

        let nodeChildren = newNode.children;

        nodeChildren[offset].value = courseNodes[i].course.code;
        nodeChildren[1 + offset].value = String(courseNodes[i].hardReqs.map(req => req.code));
        nodeChildren[2 + offset].value = String(courseNodes[i].softReqs.map(req => req.code));
        nodeChildren[3 + offset].value = courseNodes[i].specialReqs;
        nodeChildren[4 + offset].checked = courseNodes[i].distCourse; //isDistCourse

        document.getElementById("node-list").appendChild(newNode);
    }

    updateCourseNodeList();
}


//===================== UPDATE MAJOR ==============================================================================

async function updateMajor()
{
    updateCourseNodeList();

    // console.log(courseNodeList);

    let majorUpdateRequest = {
            "code": document.getElementById("major-code").innerText,
            "name": document.getElementById("major-name").value,
            "courseNodeShellList": courseNodeList,
            "distCredits": parseInt(document.getElementById("major-dist-credits").value),
            "electCredits": parseInt(document.getElementById("major-elect-credits").value)
    }

    let url = domain + "/api/admin/major/" + majorUpdateRequest.code + "/update"

    await fetch(url, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(majorUpdateRequest)
    })
        .then(response => alert("Success updating major"))
        .catch(error => alert("Failed to update major"))

}


//===================== DRAGGABLE SCRIPT ==============================================================================


document.getElementById("node-list").addEventListener("dragover", e =>
{
    e.preventDefault();
    const afterElement = getDragAfterElement(document.getElementById("node-list"), e.clientY);
    const draggable = document.querySelector('.dragging');

    if (afterElement == null)
        document.getElementById("node-list").appendChild(draggable);
    else
        document.getElementById("node-list").insertBefore(draggable, afterElement);
})

function getDragAfterElement(container, y)
{
    const draggableElements = [...container.querySelectorAll(".draggable-course-node:not(.dragging)")];

    return draggableElements.reduce((closest, child) =>
    {
        const box = child.getBoundingClientRect();
        const offset = y - box.top - (box.height / 2);

        if (offset < 0 && offset > closest.offset)
            return { offset: offset, element: child };
        else
            return closest;

    }, { offset: Number.NEGATIVE_INFINITY }).element;
}
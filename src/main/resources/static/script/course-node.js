`
<div class="draggable-course-node" draggable="true">
    <div class="draggable-symbol">
        <span class="material-symbols-outlined">drag_indicator</span>
    </div>
    <button type="button" class="remove-node-button" onclick="removeCourseNode(this)">
        <span class="material-icons" >close</span>
    </button>
    <input class="course" type="text" oninput="updateCourseNodeList()" placeholder="Course">
    <textarea class="textarea" oninput="autoGrow(this); updateCourseNodeList();" placeholder="Hard Requirements"></textarea>
    <textarea class="textarea" oninput="autoGrow(this); updateCourseNodeList();" placeholder="Soft Requirements"></textarea>
    <textarea class="textarea" oninput="autoGrow(this)" placeholder="Special Requirements"></textarea>
    <input class="checkbox" type="checkbox" id="isDistCourse">
    <label for="isDistCourse">Distributive</label>
</div>
`

class CourseNode extends HTMLDivElement
{
    courseInput;
    hardReqsTextarea;
    softReqsTextarea;
    specialReqsTextarea;
    distCheckbox;
    nodeIndex;

    constructor(courseCode, hardReqsCodes, softReqsCodes, specialReqs, isDistCourse, nodeIndex)
    {
        super();

        this.className = "draggable-course-node";
        this.draggable = true;

        this.courseInput = new CourseCodeInput(courseCode);
        this.appendChild(this.courseInput);

        this.hardReqsTextarea = new ReqsTextarea(hardReqsCodes);
        this.appendChild(this.hardReqsTextarea);

        this.softReqsTextarea = new ReqsTextarea(softReqsCodes);
        this.appendChild(this.softReqsTextarea);

        this.specialReqsTextarea = document.createElement("textarea");
        this.specialReqsTextarea.value = specialReqs;
        this.appendChild(this.specialReqsTextarea);

        this.distCheckbox = new DistCourseCheckbox(isDistCourse);
        this.appendChild(this.distCheckbox);

        this.index = nodeIndex;
    }

    getAsObject()
    {
        let courseNode = {
            "courseCode": this.courseInput.value,
            "hardReqsCodes": this.hardReqsTextarea.getAsArray(),
            "softReqsCodes": this.softReqsTextarea.getAsArray(),
            "specialReqs": this.specialReqsTextarea.value,
            "isDistCourse": this.distCheckbox.checked,
            "index": this.index,
        };
    }
}

class CourseCodeInput extends HTMLInputElement
{
    constructor(value)
    {
        super();
        this.type = "text";
        this.value = (value === null) ? "" : value;
    }
}

class ReqsTextarea extends HTMLTextAreaElement
{
    constructor(value)
    {
        super();
        this.value = (value === null) ? "" : value;
    }

    getAsArray()
    {
        let reqs= this.value.split(",").map(code => { return code.trim(); });
        return (reqs[0] === "") ? [] : reqs;
    }
}

class DistCourseCheckbox extends HTMLInputElement
{
    constructor(checked)
    {
        super();
        this.type = "checkbox";
        this.checked = checked;
    }
}

customElements.define("course-node", CourseNode, { extends: "div" });
customElements.define("course-code-input", CourseCodeInput, { extends: "input" });
customElements.define("reqs-textarea", ReqsTextarea, { extends: "textarea" });
customElements.define("dist-course-checkbox", DistCourseCheckbox, { extends: "input" });
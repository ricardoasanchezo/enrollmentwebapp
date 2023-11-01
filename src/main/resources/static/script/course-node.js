const courseNodeTemplate = document.createElement('template');
courseNodeTemplate.innerHTML = `
<style>
    :host * {
        box-sizing: border-box;
        border: none;
        font-size: 1rem;
        min-height: 2rem;
    }

    :host {
        background-color: transparent;
        display: grid;
        grid-template-columns: 30px 30px auto 30px 1fr 1fr 2fr 30px;
        grid-gap: 10px;
        /*padding: 5px;*/
        align-items: center;
        justify-items: center;
        width: fit-content;
    }
    
    #remove-node-button {
        min-width: 30px;
        background-color: red;
        color: white;
    }
    
    #remove-node-button:hover {
        background-color: darkred;
    }
    
    textarea {
        resize: none;
        width: 100%;
    }
    
    #is-dist-course {
        min-height: 25px;
        min-width: 25px;
        border-radius: 50%;
    }
    
    .empty {
        color: black;
        background-color: white;
    }
    
    .ok {
        color: darkgreen;
        background-color: lightgreen;
    }
    
    .error {
        color: darkred;
        background-color: lightpink;
    }
    
</style>

<img src="/images/drag_indicator.svg" alt="drag" style="cursor: move">
<button type="button" id="remove-node-button">X</button>
<input id="course-code" type="text" size="10">
<select>
    <option value="A">A</option>
    <option value="B">B</option>
    <option value="C">C</option>
    <option value="D">D</option>
    <option value="F">F</option>
</select>
<input id="hard-reqs">
<input id="soft-reqs">
<textarea id="special-reqs"></textarea>
<input id="is-dist-course" type="checkbox">
`;

class CourseNode extends HTMLElement
{
    courseCodeInput;
    passingGradeSelect;
    hardReqsInput;
    softReqsInput;
    specialReqsTextarea;
    distCourseCheckbox;

    constructor(
        container,
        courseCode = '',
        passingGrade = 'C',
        hardReqs = '',
        softReqs = '',
        specialReqs = '',
        isDistCourse = false,
        nodeIndex = 0)
    {
        super();

        this.attachShadow({mode: 'open'});
        this.shadowRoot.appendChild(courseNodeTemplate.content.cloneNode(true));

        this.shadowRoot.querySelector('#remove-node-button').addEventListener('click', () => container.removeCourseNode(this));

        this.courseCodeInput = this.shadowRoot.querySelector('#course-code');
        this.courseCodeInput.value = courseCode;

        this.passingGradeSelect = this.shadowRoot.querySelector('select');
        this.passingGradeSelect.value = passingGrade;

        this.hardReqsInput = this.shadowRoot.querySelector('#hard-reqs');
        this.hardReqsInput.value = hardReqs;

        this.softReqsInput = this.shadowRoot.querySelector('#soft-reqs');
        this.softReqsInput.value = softReqs;

        this.specialReqsTextarea = this.shadowRoot.querySelector('#special-reqs');
        this.specialReqsTextarea.value = specialReqs;

        this.distCourseCheckbox = this.shadowRoot.querySelector('#is-dist-course');
        this.distCourseCheckbox.checked = isDistCourse;

        this.draggable = true;

        this.addEventListener("dragstart",()=>
        {
            this.classList.add('dragging');
        });
        this.addEventListener("dragend",()=>
        {
            this.classList.remove('dragging');
        });
    }

    toDto(index = 0)
    {
        // This should return an object like the CourseNodeDto
        return {
            "code": this.getCourseCode(),
            "passingGrade": this.getPassingGrade(),
            "hardReqs": this.getHardReqs(),
            "softReqs": this.getSoftReqs(),
            "specialReqs": this.getSpecialReqs(),
            "isDistCourse": this.getIsDistCourse(),
            "nodeIndex": index
        };
    }

    validateCourseCode(allCodes, currentCodes)
    {
        let code = this.getCourseCode();

        let isValid = allCodes.includes(code) && !currentCodes.includes(code);

        this.applyStyle(this.courseCodeInput, isValid ? 'ok' : 'error');

        return isValid;
    }

    validateHardReqs(currentCodes)
    {
        let reqs = this.getHardReqs();

        if (reqs.length === 0)
        {
            this.applyStyle(this.hardReqsInput, 'empty');
            return true;
        }

        let isValid = reqs.every(req => currentCodes.includes(req));

        this.applyStyle(this.hardReqsInput, isValid ? 'ok' : 'error');

        return isValid;
    }

    validateSoftReqs(allCodes, currentCodes)
    {
        let reqs = this.getSoftReqs();

        if (reqs.length === 0)
        {
            this.applyStyle(this.softReqsInput, 'empty');
            return true;
        }

        let isValid =
            reqs.length > 1 &&
            reqs.some(req => currentCodes.includes(req)) &&
            reqs.every(req => allCodes.includes(req));

        this.applyStyle(this.softReqsInput, isValid ? 'ok' : 'error');

        return isValid;
    }

    getCourseCode()
    {
        return this.courseCodeInput.value.trim();
    }

    getPassingGrade()
    {
        return this.passingGradeSelect.value;
    }

    getHardReqs()
    {
        let reqArray = this.hardReqsInput.value.split(",").map(code => { return code.trim(); });
        return (reqArray[0] === "") ? [] : reqArray;
    }

    getSoftReqs()
    {
        let reqArray = this.softReqsInput.value.split(",").map(code => {
            return code.trim();
        });
        return (reqArray[0] === "") ? [] : reqArray;
    }

    getSpecialReqs()
    {
        return this.specialReqsTextarea.value;
    }

    getIsDistCourse()
    {
        return this.distCourseCheckbox.checked;
    }

    applyStyle(element, className)
    {
        element.className = className;
    }
}

window.customElements.define('course-node', CourseNode);

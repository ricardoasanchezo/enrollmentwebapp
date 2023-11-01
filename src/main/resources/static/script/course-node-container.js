const courseNodeContainerTemplate = document.createElement('template');
courseNodeContainerTemplate.innerHTML = `
<style>
    :host * {
        box-sizing: border-box;
    }
    
    :host {
        display: block;
        background-color: whitesmoke;
    }
    
    #major-info {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        width: fit-content;
        margin-bottom: 2rem;
    }

    #list {
        background-color: var(--light-tertiary);
        display: block;
    }
    
    #button-container {
        display: flex;
        flex-direction: row;
    }
    
    button {
        padding: 10px;
    }
    
    .dragging {
        opacity: .4;
    }
    
</style>

<div id="major-info">
    <div>Code:</div>
    <div id="major-code"></div>
    
    <label for="major-name">Name:</label>
    <input id="major-name">
    
    <label for="major-dist-credits">Distributive Credits Requirement:</label>
    <input id="major-dist-credits">
    
    <label for="major-elect-credits">Elective Credits Requirement:</label>
    <input id="major-elect-credits">
</div>

<div id="list"></div>

<div id="button-container">
    <button type="button" id="add-button">Add new course</button>
    <button type="button" id="save-button">Save</button>
</div>
`;

class CourseNodeContainer extends HTMLElement
{
    majorCode;
    majorNameInput;
    distCreditsInput;
    electCreditsInput;

    list;
    allCodes;
    currentCodes;

    addButton;
    saveButton;

    constructor(allCodes, major)
    {
        super();

        this.allCodes = allCodes;
        this.currentCodes = [];

        this.attachShadow({mode: 'open'});
        this.shadowRoot.appendChild(courseNodeContainerTemplate.content.cloneNode(true));

        this.majorCode = this.shadowRoot.querySelector('#major-code');
        this.majorCode.innerText = major.code;

        this.majorNameInput = this.shadowRoot.querySelector('#major-name');
        this.majorNameInput.value = major.name;

        this.distCreditsInput = this.shadowRoot.querySelector('#major-dist-credits');
        this.distCreditsInput.value = major.distCredits;

        this.electCreditsInput = this.shadowRoot.querySelector('#major-elect-credits');
        this.electCreditsInput.value = major.electCredits;

        this.list = this.shadowRoot.querySelector('#list');
        this.list.addEventListener('input', () => this.validate());

        this.addButton = this.shadowRoot.querySelector('#add-button');
        this.addButton.addEventListener('click', () => this.addCourseNode())

        this.saveButton = this.shadowRoot.querySelector('#save-button');
        this.saveButton.addEventListener('click', () => this.saveChanges());

        this.list.addEventListener("dragover", e =>
        {
            e.preventDefault();
            const afterElement = this.getDragAfterElement(this.list, e.clientY);
            const draggable = this.shadowRoot.querySelector('.dragging');

            if (afterElement == null)
                this.list.appendChild(draggable);
            else
                this.list.insertBefore(draggable, afterElement);

            this.validate()
        });

        let courseNodes = major.courseNodes;

        courseNodes.sort(function(a, b) { return a.nodeIndex - b.nodeIndex });

        for (let i = 0; i < courseNodes.length; i++)
        {
            let courseNode = new CourseNode(
                this,
                courseNodes[i].code,
                courseNodes[i].passingGrade,
                courseNodes[i].hardReqs,
                courseNodes[i].softReqs,
                courseNodes[i].specialReqs,
                courseNodes[i].isDistCourse,
                i
            );

            this.addCourseNode(courseNode)
        }
    }

    addCourseNode(courseNode = new CourseNode(this))
    {
        this.list.appendChild(courseNode);
        this.validate();
    }

    removeCourseNode(node)
    {
        node.remove();
        this.validate();
    }

    validate()
    {
        this.currentCodes = [];

        let courseNodes = this.list.childNodes;
        let areAllNodesValid = courseNodes.length > 0;

        courseNodes.forEach(courseNode =>
        {
            let isCourseCodeValid = courseNode.validateCourseCode(this.allCodes, this.currentCodes);
            if (isCourseCodeValid)
                this.currentCodes.push(courseNode.getCourseCode());

            let isHardReqsValid = courseNode.validateHardReqs(this.currentCodes);
            let isSoftReqsValid = courseNode.validateSoftReqs(this.allCodes, this.currentCodes);

            let isNodeValid = isCourseCodeValid && isHardReqsValid && isSoftReqsValid;

            if (!isNodeValid)
                areAllNodesValid = false;
        });

        this.saveButton.disabled = !areAllNodesValid;
    }

    async saveChanges()
    {
        let updateRequest = {
            "code": this.majorCode.innerText,
            "name": this.majorNameInput.value,
            "courseNodes": this.getDtoList(),
            "distCredits": parseInt(this.distCreditsInput.value),
            "electCredits": parseInt(this.electCreditsInput.value)
        }

        let url = domain + "/api/admin/major/" + updateRequest.code + "/update"

        let response = await fetch(url, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(updateRequest)
        });

        if (!response.ok)
        {
            alert("Failed to update major");
            return;
        }

        alert("Success updating major")
    }

    getDtoList()
    {
        let dtoList = [];
        let i = 0;
        this.list.childNodes.forEach(courseNode =>
        {
            dtoList.push(courseNode.toDto(i++));
        });

        console.log(dtoList);

        return dtoList;
    }


    getDragAfterElement(container, y)
    {
        const draggableElements = [...container.querySelectorAll("course-node:not(.dragging)")];

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
}

window.customElements.define('course-node-container', CourseNodeContainer);
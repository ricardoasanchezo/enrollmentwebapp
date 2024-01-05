class CourseCard extends HTMLElement
{
    static STATE_MAP = {
        APPROVED: {
            icon: 'done',
            color: 'green',
            alt: 'This course has been approved'
        },
        BLOCKED: {
            icon: 'close',
            color: 'red',
            alt: 'This course is blocked by requisites'
        },
        PENDING: {
            icon: 'pending',
            color: 'grey',
            alt: 'This course is available for enrollment'
        }
    }

    static WIDTH = '350px';
    static HEIGHT = '60px';

    state;
    code;
    title;
    credits;
    isDistCourse;

    constructor(code, title, credits, state, isDistCourse)
    {
        super();

        // this.setAttribute('code', code);
        // this.setAttribute('title', title);
        // this.setAttribute('credits', credits);
        // this.setAttribute('state', state);

        this.code = code;
        this.title = title;
        this.credits = credits;
        this.state = CourseCard.STATE_MAP[state];

        this.isDistCourse = isDistCourse;

        this.attachShadow({mode: 'open'});
    }

    connectedCallback()
    {
        this.render();
    }

    render()
    {
        this.shadowRoot.innerHTML = `
        <style>
            :root {
                --course-card-width: 350px;
                --course-card-height: 350px;
            }

            :host {
                display: flex;
                flex-direction: row;
                background-color: whitesmoke;
                height: ${CourseCard.HEIGHT};
                width: ${CourseCard.WIDTH};

                /*margin: 5px;*/

                box-shadow: 0 4px 4px -4px #222
            }

            :host * {
                box-sizing: border-box;
            }

            .icon-container {
                background-color: ${this.state.color};

                min-width: ${CourseCard.HEIGHT};
                max-width: ${CourseCard.HEIGHT};
                min-height: ${CourseCard.HEIGHT};
                max-height: ${CourseCard.HEIGHT};

                display: flex;
                justify-content: center;
                align-items: center;
            }

            .icon-container img {                
                width: 35px;
                height: 35px;
            }

            .course-code {
                font-weight: bold;
                margin: 10px 0 5px 10px;
            }

            .course-name {
                font-style: italic;
                font-size: 12px;
                margin-left: 10px ;
            }

            .course-credit {
                font-size: 1.5rem;
                width: ${CourseCard.HEIGHT};
                height: ${CourseCard.HEIGHT};
                display: flex;
                justify-content: center;
                align-items: center;
                margin-left: auto;
            }
        </style>
        
        <div class="icon-container">
            <img src="../../images/icons/${this.state.icon}-white.svg" alt="${this.state.alt}">
        </div>        
        <div>
            <div class="course-code">${this.code}</div>
            <div class="course-name">${this.title}</div>
        </div>
        <span class="course-credit">${this.credits}</span>
        `;
    }

    static get observedAttributes()
    {
        return ['state', 'code', 'title', 'credits'];
    }

    attributeChangedCallback(name, oldValue, newValue)
    {
        if (this.isConnected)
        {
            switch (name)
            {
                case 'state':
                    this.state = CourseCard.STATE_MAP[newValue] || '';
                    break;
                case 'code':
                    this.code = newValue || '';
                    break;
                case 'title':
                    this.title = newValue || '';
                    break;
                case 'credits':
                    this.credits = newValue || '';
                    break;
            }

            this.render()
        }
    }
}

customElements.define('course-card', CourseCard);
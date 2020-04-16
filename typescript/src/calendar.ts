import moment from 'moment'

export class Calendar {
    
    private selectedDate: moment.Moment

    constructor() {
        this.selectedDate = moment()

        document.getElementById('year-selector')
            ?.addEventListener('change', (ev) => this.yearOrMonthChangeCallback(ev, 'year'))

        document.getElementById('month-selector')
            ?.addEventListener('change', (ev) => this.yearOrMonthChangeCallback(ev, 'month'))

        document.getElementById('day-selector')
            ?.addEventListener('change', (ev) => {
                const value = (ev.target as HTMLSelectElement).value
                this.selectedDate.date(Number(value))
        })
    }

    public getDate(): string {
        return this.selectedDate.format('YYYY/MM/DD')
    }

    private yearOrMonthChangeCallback(ev: Event, type: 'year' | 'month') {
        
        const value = (ev.target as HTMLSelectElement).value
        this.selectedDate.set(type, Number(value))

        this.renderDays(this.selectedDate.year(),
                        this.selectedDate.month())

        const dayEv = document.createEvent("HTMLEvents")
        dayEv.initEvent('change')

        document.getElementById('day-selector')?.dispatchEvent(dayEv)
    }

    private renderYears(): number {
        
        const yearSelector = document.getElementById("year-selector")! as HTMLSelectElement

        yearSelector.innerHTML = ''

        const year = moment().year()
        let yearOpt: HTMLOptionElement

        for (let y = 1970; y <= year; y++) {
            yearOpt = this.createOption(y.toString(), y)
            yearSelector.appendChild(yearOpt)
        }

        (yearSelector.lastChild as HTMLOptionElement).selected = true   
        
        return year
    }

    private renderMonths(): number { 
        const monthSelector = document.getElementById("month-selector")! as HTMLSelectElement
        monthSelector.innerHTML = ''

        const monthList = ["styczeń", "luty", "marzec", "kwiecień", "maj", "czerwiec",
                           "lipiec", "sierpień", "wrzesień", "październik", "listopad", "grudzień"]

        let monthOpt: HTMLOptionElement

        const month = moment().month()

        for (let m = 0; m < 12; m++) {
            monthOpt = this.createOption(monthList[m], m)
            monthSelector.appendChild(monthOpt)
        }

        (monthSelector.childNodes[month] as HTMLOptionElement).selected = true

        return month
    }

    private renderDays(year: number, month: number) {
        const daySelector = document.getElementById('day-selector')! as HTMLSelectElement
        daySelector.innerHTML = ''

        const date = moment().year(year)
                             .month(month)

        let dayOpt: HTMLOptionElement

        for (let d = 1; d <= date.daysInMonth(); d++) {
            dayOpt = this.createOption(d.toString(), d)
            daySelector.appendChild(dayOpt)
        }
    }

    public renderCurrentDate(): void{
        const year = this.renderYears()
        const month = this.renderMonths()
        
        this.renderDays(year, month)

        const day = moment().date()

        const dayOpt = (document.getElementById('day-selector') as HTMLSelectElement).childNodes[day - 1] as HTMLOptionElement
        dayOpt.selected = true
    }

    private createOption(text: string, value: number) : HTMLOptionElement {
        const opt = document.createElement('option')

        opt.text = text
        opt.value = value.toString()

        return opt
    }
}
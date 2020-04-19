import { RemovePaymentService } from './services/removePayment.service'

class MainController {

    constructor(private removeService: RemovePaymentService) { 

        const removeBtns = document.getElementsByClassName('remove-buttons')

        Array.from(removeBtns)
            .forEach(btn => btn.addEventListener('click', ev => {
                
                removeService.removeItem(btn.id)

                let tableRow = btn.parentElement!.parentElement!
                let tableBody = tableRow!.parentElement!

                tableRow?.remove()

                if (tableBody?.childElementCount === 2) {
                    tableBody.remove()
                } else {

                    let moneyPerRow = tableRow.querySelectorAll('.money')!
                    let moneyPerBody = tableBody.querySelectorAll('.money')!

                    let summaryCell = Array.from(moneyPerBody).pop() as HTMLTableCellElement

                    const oldVal = this.getValue(summaryCell.textContent!)
                    const removedVal = this.getValue(moneyPerRow[0].textContent!)
                    const newVal = oldVal - removedVal
                    
                    summaryCell.textContent! = `${newVal.toFixed(2)} zł`
                    this.updateId(tableBody)
                }
                
                
            }))
    }

    getValue(el: String) {
          return Number(el?.split(' ')[0])

    }

    updateId(tableBody: HTMLElement) {
        let idPerBody = tableBody.querySelectorAll('.id')
        for (let i = 0; i < idPerBody.length; i++) {
            idPerBody[i].textContent = String(i + 1)
        }
    }

}

const controller = new MainController(new RemovePaymentService())
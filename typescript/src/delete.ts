import { RemovePaymentService } from './services/removePayment.service'

class MainController {

    constructor(private removeService: RemovePaymentService) { 

        const removeBtns = document.getElementsByClassName('remove-buttons')

        Array.from(removeBtns)
            .forEach(el => el.addEventListener('click', ev => {
                const btn = ev.target as HTMLButtonElement
                this.removeService.removeItem(btn.id)
                console.log(btn.id)
            }))

        // document.getElementById('modal-btn')
        //     ?.addEventListener('click', () => {
        //         let modalStyle = document.getElementById('search-modal')?.style

        //         if (modalStyle)
        //         if (modalStyle)
        //             modalStyle.display = 'block'
        // })

        // window.onclick = (ev : MouseEvent) => {
        //     let modal = document.getElementById('search-modal')
        //     if (ev.target == modal) {
        //         if (modal?.style)
        //             modal.style.display = 'none'
        //     }
        // }
    }

}

const controller = new MainController(new RemovePaymentService())
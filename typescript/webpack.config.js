const path = require("path")

module.exports = {
    entry: {
        add: './js/add.js',
        delete: './js/delete.js',
        searchModal: './js/controllers/searchModal.js'
    },
    output: {
        path: path.resolve(__dirname, 'public/scripts'),
        filename: '[name]-bundle.js'
    }
}
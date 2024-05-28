const mongoose=require('mongoose');
const Scheme = mongoose.Schema;

const Laptop = new Scheme({
    hinhanh: {type:String, required: false},
    ten: {type: String, required:true},
    gia: {type: String, required:true},
    thuonghieu: {type: String, required:true},
    xuatxu: {type: String, required:true}, 
},{
    timestamps: true
})

module.exports = mongoose.model('laptops',Laptop)
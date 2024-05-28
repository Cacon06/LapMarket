const mongoose=require('mongoose')
mongoose.set('strictQuery', true)
const atlats = "mongodb+srv://admin:adm123@cluster0.m3a3iay.mongodb.net/nhom4"
const connect = async()=>{
    try {
        await mongoose.connect(atlats,
            {
             
            })
            console.log('connect success');
    } catch (error) {
        console.log(error);
        console.log('connect fail');
    }
}
module.exports = {connect}
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../bkendintigration/api";

export default function Register() {

    const [formData, setFormData] = useState({
        regNo: "",
        nic: "",
        name: "",
        email: "",
        contact: "",
        password: "",
    });

    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage("");
        try{
            const res = await api.post("/api/admins", formData);
            setMessage("Registration successful!");
            console.log(res.data);
            setTimeout(() => navigate("/login"), 1200);
        }catch(err){
            setMessage("Registration failed!"+ (err?.response?.data || err.message));
            console.error(err);
        }
    };

    return (
        <div className="max-w-lg mx-auto px-1 py-15">
            
                <h1 className="text-3xl font-bold mb-1">Admin Register</h1>
                <p className="text-sm text-white/60 mb-6">Create a new administrator account to manage events.</p>
                <form onSubmit={handleSubmit} className="bg-ink/60 border border-white/10 rounded-xl flex flex-col p-6 space-y-4">

                    <div className="space-y-1">
                        <label className="text-sm text-white/80">Registration No</label>
                    <input type="text" name="regNo" placeholder="EGxxxxxxxx" className="w-full bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold" onChange={handleChange} required/>
                    </div>

                    <div className="space-y-1">
                        <label className="text-sm text-white/80">NIC</label>
                    <input type="text" name="nic" placeholder="************" className="w-full bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold" onChange={handleChange}  required/>
                    </div>

                    <div className="space-y-1">
                        <label className="text-sm text-white/80">Full Name</label>
                    <input type="text" name="name" placeholder="Sarath Kumara" className="w-full bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold" onChange={handleChange} required/>
                    </div>

                    <div className="space-y-1">
                        <label className="text-sm text-white/80">Email</label>
                    <input type="email" name="email" placeholder="sarath@example.com" className="w-full bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold" onChange={handleChange} required/>
                    </div>

                    <div className="space-y-1">
                        <label className="text-sm text-white/80">Contact No</label>
                    <input type="text" name="contact" placeholder="xxxxxxxxxx" className="w-full bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold" onChange={handleChange} required/>
                    </div>

                    <div className="space-y-1">
                        <label className="text-sm text-white/80">Password</label>
                    <input type="password" name="password" placeholder="•••••••" className="w-full bg-black/40 border border-white/10 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-gold" onChange={handleChange} required/>
                    </div>

                    <button type="submit" className="w-full bg-gold text-ink font-semibold rounded-lg py-2 hover:opacity-90 hover:scale-[0.98] transition hover:text-maroon">Register</button>

                    {message && <p className="text-red-400 text-sm">{message}</p>}

                    <p className="text-sm text-white/80">
        Already have an account?{" "}
        <a className="text-gold hover:text-maroon hover:underline" href="/login">Login here.</a>
        </p>

                </form>
                
            
        </div>
    );

}
"use client";
import { useState } from "react";
import axios from "axios";
import { useRouter } from "next/navigation";
import styles from "./page.module.css";

export default function Register() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const router = useRouter();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setError("Please enter a valid email address.");
      return;
    }
    if (password.length < 6) {
      setError("Password must be at least 6 characters long.");
      return;
    }
    try {
      const response = await axios.post("http://localhost:8080/api/register", { email, password }, {
        headers: { "Content-Type": "application/json" },
      });
      console.log("Registration successful:", response.data);
      router.push("/login");
    } catch (err) {
      console.error("Registration error:", err.response?.data || err);
      setError("Registration failed: " + (err.response?.data?.message || err.response?.statusText || err.message || "Unknown error"));
    }
  };

  return (
    <div className={styles.page}>
      <div className={styles.main}>
        <h1 className={styles.title}>Join Us</h1>
        {error && <div className={styles.error}>{error}</div>}
        <form onSubmit={handleSubmit} className={styles.form}>
          <div className={styles.formGroup}>
            <label className={styles.label}>Email</label>
            <input 
              type="email" 
              value={email} 
              onChange={(e) => setEmail(e.target.value)} 
              required 
              className={styles.input}
              placeholder="Enter your email"
            />
          </div>
          <div className={styles.formGroup}>
            <label className={styles.label}>Password</label>
            <input 
              type="password" 
              value={password} 
              onChange={(e) => setPassword(e.target.value)} 
              required 
              className={styles.input}
              placeholder="Create a password"
            />
          </div>
          <button type="submit" className={styles.button}>Create Account</button>
        </form>
        <div className={styles.link}>
          Already have an account? <a href="/login">Sign in here</a>
        </div>
      </div>
    </div>
  );
}
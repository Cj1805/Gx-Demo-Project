import React from 'react'
import '../styles/Footer.css'

const Footer = () => {
    return (
        <footer className='site-footer fluid'>
            <div class="flex">
                <div className='flex-item-link' datatype='menu'><h6>QUICK LINKS</h6>
                    <ul class="site-footer__linklist">
                        <li><a href="/pages/ask-the-experts">Ask the Experts</a></li>
                        <li><a href="/a/blog">Blog</a></li>
                        <li><a href="http://careers.himalayawellness.com/">Careers</a></li>
                        <li><a href="/pages/contact-us-old">Contact Us</a></li>
                        <li><a href="https://himalayawellness.in/pages/faqs">FAQs</a></li>
                        <li><a href="https://healthhelp.himalayawellness.in/">Health Help</a></li>
                        <li><a href="https://herbfinder.himalayawellness.in/">Herb Finder</a></li>
                        <li><a href="/pages/himalayasmiles">Loyalty Program</a></li>
                        <li><a href="https://researchpapers.himalayawellness.in/">Research Papers</a></li>
                        <li><a href="https://storelocator.himalayawellness.in/">Store Locator</a></li>
                        <li><a href="/policies/refund-policy">Refund policy</a></li>
                        <li><a href="/policies/terms-of-service">Terms of Service</a></li>
                    </ul>
                </div>
                <div className='flex-item-link' datatype='menu'><h6>POLICIES</h6>
                    <ul class="site-footer__linklist">
                        <li><a href="/pages/shipping">Shipping</a></li>
                        <li><a href="/pages/returns">Returns & Cancellation </a></li>
                        <li><a href="/pages/terms-of-use">Terms of Use</a></li>
                        <li><a href="/pages/privacy-policy">Privacy Policy</a></li>
                    </ul>
                </div>
                <div className='flex-item' datatype='menu'><h6>SUBSCRIBE TO OUR NEWSLETTER</h6>
                    <ul>
                        <p>By entering your email, you agree to our Terms of Service and Privacy Policy.</p>
                    </ul>
                </div>
                <div className='flex-item' datatype='menu'><h6>CONTACT US</h6>
                    <ul>
                        <p>Himalaya Wellness Company, Makali, Bengaluru - 562162</p>
                        <p>Call Us: 1–800–208–1930<br />
                            Mon-Fri: 9:00am - 5:00pm</p>
                        <p>Email Us:<a href='#email'> contactus@himalayawellness.com </a></p>
                        <p>WhatsApp Us: <a href='#whatsapp'> 89518 91930 </a></p>
                        <p><a href='#report'>Report Product Related Issues</a></p>
                    </ul>
                </div>
            </div>
            <div className='baseFooter'>
            <div className='copyright p-2'>
                © 2024 Himalaya Wellness Company. All Rights Reserved.
            </div>
            <div className='description p-2'>Information on this website is provided for informational purposes and is not a substitute for advice from your own physician or other medical professionals.This website is meant for use by Indian residents only. The product pack shots on the website are for representation purposes only.The actual product may have slight variations in appearance and price.</div>
            </div>
            </footer>
    )
}

export default Footer
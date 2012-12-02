package org.wl.core.util.jcaptcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.jhlabs.image.WaterFilter;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class DefaultEngine extends ListImageCaptchaEngine {

	@Override
	protected void buildInitialFactories() {
		WordGenerator wgen = new RandomWordGenerator("0123456789");
		RandomRangeColorGenerator cgen = new RandomRangeColorGenerator(new int[] { 0, 100 }, new int[] { 0, 100 },new int[] { 0, 100 });

		// 图片的大小
		BackgroundGenerator backgroundGenerator = new GradientBackgroundGenerator(80, 25,Color.white, Color.white);
		//FunkyBackgroundGenerator
		//文字干扰器--- 可以创建多个
//		BaffleTextDecorator baffleTextDecorator = new BaffleTextDecorator(2,cgen);//气泡干扰
//		LineTextDecorator lineTextDecorator = new LineTextDecorator(1,cgen);//曲线干扰
		TextDecorator[] textDecorators = new TextDecorator[0];
//		textDecorators[0] = baffleTextDecorator;
		//textDecorators[0] = lineTextDecorator;
		
		// 文字显示4个数
//		TextPaster textPaster = new RandomTextPaster(new Integer(4),new Integer(4), cgen, true,textDecorators);
		TextPaster textPaster = new DecoratedRandomTextPaster(new Integer(4),new Integer(4), cgen, true,textDecorators);
		
		// 字体格式
		Font[] fontsList = new Font[] { new Font("Arial", 0, 10), new Font("Tahoma", 0, 10), new Font("Verdana", 0, 10), };
		
		// 文字的大小
		FontGenerator fontGenerator = new RandomFontGenerator(new Integer(15), new Integer(15), fontsList);
		
		//过滤器
        WaterFilter water = new WaterFilter();
        water.setAmplitude(4d);//振幅
        water.setAntialias(true);//显示字会出现锯齿状,true就是平滑的
        //water.setPhase(30d);//月亮的盈亏 
        water.setWavelength(60d);//
        
		ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[]{});
        ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[]{});
        ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[]{water});
		// 生成图片输出
//		WordToImage wordToImage = new ComposedWordToImage(fontGenerator,backgroundGenerator, textPaster);
		WordToImage word2image  = new DeformedComposedWordToImage(fontGenerator, backgroundGenerator, textPaster,backDef,textDef,postDef);
		
		this.addFactory(new GimpyFactory(wgen, word2image));
	}

}

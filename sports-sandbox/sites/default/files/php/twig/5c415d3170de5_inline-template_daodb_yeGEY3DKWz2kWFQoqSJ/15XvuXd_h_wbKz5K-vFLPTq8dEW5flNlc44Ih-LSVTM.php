<?php

/* {# inline_template_start #}<strong>Start Date:</strong> {{ field_match_start_date }} <br>
<strong>End Date:</strong> 	{{ field_match_end_date }} <br>
<strong>Venue:</strong> {{ field_match_venue }}
 */
class __TwigTemplate_4b64bcd7c7f9f3898e74a47b594cc4cbfeb8bbe35380b93417c00fd9eef07429 extends Twig_Template
{
    public function __construct(Twig_Environment $env)
    {
        parent::__construct($env);

        $this->parent = false;

        $this->blocks = array(
        );
    }

    protected function doDisplay(array $context, array $blocks = array())
    {
        $tags = array();
        $filters = array();
        $functions = array();

        try {
            $this->env->getExtension('Twig_Extension_Sandbox')->checkSecurity(
                array(),
                array(),
                array()
            );
        } catch (Twig_Sandbox_SecurityError $e) {
            $e->setSourceContext($this->getSourceContext());

            if ($e instanceof Twig_Sandbox_SecurityNotAllowedTagError && isset($tags[$e->getTagName()])) {
                $e->setTemplateLine($tags[$e->getTagName()]);
            } elseif ($e instanceof Twig_Sandbox_SecurityNotAllowedFilterError && isset($filters[$e->getFilterName()])) {
                $e->setTemplateLine($filters[$e->getFilterName()]);
            } elseif ($e instanceof Twig_Sandbox_SecurityNotAllowedFunctionError && isset($functions[$e->getFunctionName()])) {
                $e->setTemplateLine($functions[$e->getFunctionName()]);
            }

            throw $e;
        }

        // line 1
        echo "<strong>Start Date:</strong> ";
        echo $this->env->getExtension('Twig_Extension_Sandbox')->ensureToStringAllowed($this->env->getExtension('Drupal\Core\Template\TwigExtension')->escapeFilter($this->env, ($context["field_match_start_date"] ?? null), "html", null, true));
        echo " <br>
<strong>End Date:</strong> \t";
        // line 2
        echo $this->env->getExtension('Twig_Extension_Sandbox')->ensureToStringAllowed($this->env->getExtension('Drupal\Core\Template\TwigExtension')->escapeFilter($this->env, ($context["field_match_end_date"] ?? null), "html", null, true));
        echo " <br>
<strong>Venue:</strong> ";
        // line 3
        echo $this->env->getExtension('Twig_Extension_Sandbox')->ensureToStringAllowed($this->env->getExtension('Drupal\Core\Template\TwigExtension')->escapeFilter($this->env, ($context["field_match_venue"] ?? null), "html", null, true));
        echo "
";
    }

    public function getTemplateName()
    {
        return "{# inline_template_start #}<strong>Start Date:</strong> {{ field_match_start_date }} <br>
<strong>End Date:</strong> \t{{ field_match_end_date }} <br>
<strong>Venue:</strong> {{ field_match_venue }}
";
    }

    public function isTraitable()
    {
        return false;
    }

    public function getDebugInfo()
    {
        return array (  55 => 3,  51 => 2,  46 => 1,);
    }

    /** @deprecated since 1.27 (to be removed in 2.0). Use getSourceContext() instead */
    public function getSource()
    {
        @trigger_error('The '.__METHOD__.' method is deprecated since version 1.27 and will be removed in 2.0. Use getSourceContext() instead.', E_USER_DEPRECATED);

        return $this->getSourceContext()->getCode();
    }

    public function getSourceContext()
    {
        return new Twig_Source("", "{# inline_template_start #}<strong>Start Date:</strong> {{ field_match_start_date }} <br>
<strong>End Date:</strong> \t{{ field_match_end_date }} <br>
<strong>Venue:</strong> {{ field_match_venue }}
", "");
    }
}
